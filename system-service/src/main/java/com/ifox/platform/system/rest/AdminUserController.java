package com.ifox.platform.system.rest;

import com.ifox.platform.common.exception.BuildinSystemException;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.response.*;
import com.ifox.platform.system.entity.AdminUserEO;
import com.ifox.platform.system.entity.RoleEO;
import com.ifox.platform.system.exception.NotFoundAdminUserException;
import com.ifox.platform.system.request.adminuser.*;
import com.ifox.platform.system.response.AdminUserVO;
import com.ifox.platform.system.response.RoleVO;
import com.ifox.platform.system.service.AdminUserService;
import com.ifox.platform.system.service.RoleService;
import com.ifox.platform.utility.common.DigestUtil;
import com.ifox.platform.utility.common.EncodeUtil;
import com.ifox.platform.utility.common.PasswordUtil;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.jwt.JWTUtil;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import com.jsoniter.JsonIterator;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ifox.platform.common.constant.RestStatusConstant.*;

@Api(tags = "后台用户管理")
@Controller
@RequestMapping(value = "/adminUser", headers = {"api-version=1.0", "Authorization"})
@SuppressWarnings("unchecked")
public class AdminUserController extends BaseController<AdminUserVO> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private RoleService roleService;


    @ApiOperation("保存用户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({ @ApiResponse(code = 701, message = "登录名已经存在")})
    public @ResponseBody BaseResponse save(@ApiParam @RequestBody @Validated AdminUserSaveRequest adminUserSaveRequest, @RequestHeader("Authorization") String token){
        String uuid = UUIDUtil.randomUUID();
        logger.info("保存用户信息 adminUserSaveRequest:{}, uuid:{}", adminUserSaveRequest, uuid);

        String loginName = adminUserSaveRequest.getLoginName();
        AdminUserEO byLoginName = adminUserService.getByLoginName(loginName);
        if (byLoginName != null) {
            logger.info("登录名已经存在 uuid:{}", uuid);
            return new BaseResponse(EXISTED_LOGIN_NAME, "登录名已经存在");
        }

        AdminUserEO adminUserEO = ModelMapperUtil.get().map(adminUserSaveRequest, AdminUserEO.class);

        adminUserEO.setCreator(getUserIdByToken(token));

        byte[] bytes = DigestUtil.generateSalt(PasswordUtil.SALT_SIZE);
        String salt = EncodeUtil.encodeHex(bytes);

        adminUserEO.setSalt(salt);
        adminUserEO.setPassword(PasswordUtil.encryptPassword(adminUserSaveRequest.getPassword(), salt));

        String[] checkedRoleArray = adminUserSaveRequest.getCheckedRole();
        if (checkedRoleArray != null && checkedRoleArray.length > 0) {
            List<RoleEO> roleEOList = new ArrayList<>();
            for (String roleId : checkedRoleArray) {
                RoleEO roleEO = roleService.get(roleId);
                roleEOList.add(roleEO);
            }
            adminUserEO.setRoleEOList(roleEOList);
        }

        adminUserService.save(adminUserEO);

        logger.info(successSave + " uuid:{}", uuid);
        return successSaveBaseResponse();
    }

    @ApiOperation("删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiResponses({ @ApiResponse(code = 404, message = "用户不存在"),
                    @ApiResponse(code = 400, message = "无效请求：ids为空"),
                    @ApiResponse(code = 705, message = "不允许删除自身账号")})
    public @ResponseBody BaseResponse delete(@ApiParam @RequestBody String[] ids, @RequestHeader("Authorization") String token, HttpServletResponse response){
        String uuid = UUIDUtil.randomUUID();
        logger.info("删除用户 ids:{}, uuid:{}", Arrays.toString(ids), uuid);

        if (ids.length == 0){
            logger.info("无效请求,ids为空 uuid:{}", uuid);
            return invalidRequestBaseResponse(response);
        }

        String userId = getUserIdByToken(token);
        if (Arrays.asList(ids).contains(userId)) {
            logger.info("不允许删除自身账号 userId:{}, uuid:{}", userId, uuid);
            return deleteSelfErrorBaseResponse(response);
        }

        try {
            adminUserService.delete(ids);
        } catch (NotFoundAdminUserException e) {
            logger.info("删除的用户不存在 uuid:{}", uuid);
            return notFoundBaseResponse("删除的用户不存在", response);
        } catch (BuildinSystemException e) {
            logger.info("系统内置用户不允许删除 uuid:{}", uuid);
            return deleteBuildinSystemErrorBaseResponse("系统内置用户不允许删除", response);
        }

        logger.info(successDelete + " uuid:{}", uuid);
        return successDeleteBaseResponse();
    }

    @ApiOperation("更新用户")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiResponses({ @ApiResponse(code = 404, message = "用户不存在"),
                    @ApiResponse(code = 701, message = "登录名已经存在")})
    public @ResponseBody BaseResponse update(@ApiParam @RequestBody @Validated AdminUserUpdateRequest updateRequest, HttpServletResponse response){
        String uuid = UUIDUtil.randomUUID();
        logger.info("更新用户信息 updateRequest:{}, uuid:{}", updateRequest.toString(), uuid);

        String id = updateRequest.getId();
        AdminUserEO adminUserEO = adminUserService.get(id);
        if (adminUserEO == null) {
            logger.info("用户不存在 uuid:{}", uuid);
            return super.notFoundBaseResponse("用户不存在", response);
        }

        String loginName = updateRequest.getLoginName();
        if (!adminUserEO.getLoginName().equals(loginName)) {
            AdminUserEO byLoginName = adminUserService.getByLoginName(loginName);
            if (byLoginName != null) {
                logger.info("登录名已经存在 uuid:{}" ,uuid);
                return new BaseResponse(EXISTED_LOGIN_NAME, "登录名已经存在");
            }
        }

        adminUserService.update(updateRequest);

        logger.info(successUpdate + " uuid:{}", uuid);
        return successUpdateBaseResponse();
    }

    @ApiOperation("单个用户信息查询")
    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 404, message = "此用户不存在") })
    @SuppressWarnings("unchecked")
    public @ResponseBody OneResponse<AdminUserVO> get(@ApiParam @PathVariable(name = "userId") @NotBlank String userId, HttpServletResponse response){
        String uuid = UUIDUtil.randomUUID();
        logger.info("单个用户信息查询 userId:{}, uuid:{}", userId, uuid);

        AdminUserEO eo = adminUserService.get(userId);
        if (eo == null) {
            logger.info("此用户不存在 uuid:{}", uuid);
            return super.notFoundOneResponse("此用户不存在", response);
        }

        AdminUserVO vo = new AdminUserVO();
        ModelMapperUtil.get().map(eo, vo);

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryOneResponse(vo);
    }

    @ApiOperation("分页查询用户")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public PageResponse<AdminUserVO> page(@ApiParam @RequestBody AdminUserPageRequest pageRequest) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("分页查询用户 pageRequest:{}, uuid:{}", pageRequest.toString(), uuid);

        SimplePage<AdminUserEO> page = adminUserService.page(pageRequest);
        List<AdminUserEO> adminUserEOList = page.getContent();

        List<AdminUserVO> adminUserVOList = ModelMapperUtil.get().map(adminUserEOList, new TypeToken<List<AdminUserVO>>() {}.getType());

        PageResponseDetail pageResponseDetail = page.convertToPageResponseDetail();

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryPageResponse(pageResponseDetail, adminUserVOList);
    }

    @ApiOperation("获取用户列表接口")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public MultiResponse<AdminUserVO> list(@ApiParam @RequestBody AdminUserQueryRequest queryRequest){
        String uuid = UUIDUtil.randomUUID();
        logger.info("获取用户列表 queryRequest:{}, uuid:{}", queryRequest.toString(), uuid);

        List<AdminUserEO> adminUserEOList = adminUserService.list(queryRequest);
        List<AdminUserVO> adminUserVOList = ModelMapperUtil.get().map(adminUserEOList, new TypeToken<List<AdminUserVO>>() {}.getType());

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryMultiResponse(adminUserVOList);
    }

    @ApiOperation("获取用户角色列表接口")
    @RequestMapping(value = "/{userId}/role", method = RequestMethod.GET)
    @ResponseBody
    public MultiResponse<RoleVO> listRole(@ApiParam @PathVariable(name = "userId") @NotBlank String userId, HttpServletResponse response) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("获取用户角色列表 userId:{}, uuid:{}", userId, uuid);

        AdminUserEO adminUserEO = adminUserService.get(userId);
        if (adminUserEO == null) {
            logger.info("用户不存在 uuid:{}", uuid);
            response.setStatus(NOT_FOUND);
            return new MultiResponse<>(NOT_FOUND, "用户不存在", null);
        }
        List<RoleEO> roleEOList = adminUserEO.getRoleEOList();
        List<RoleVO> roleVOList = ModelMapperUtil.get().map(roleEOList, new TypeToken<List<RoleVO>>() {}.getType());

        logger.info(successQuery + " uuid:{}", uuid);
        return new MultiResponse<>(SUCCESS, successQuery, roleVOList);
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ApiResponses({ @ApiResponse(code = 461, message = "原密码错误"),
                    @ApiResponse(code = 462, message = "新密码不一致")})
    public @ResponseBody BaseResponse changePassword(@ApiParam @RequestBody @Validated AdminUserChangePwdRequest request, @RequestHeader("Authorization") String token, HttpServletResponse response) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("修改密码 request:{}, uuid:{}", request, uuid);

        String userId = getUserIdByToken(token);
        AdminUserEO adminUserEO = adminUserService.get(userId);
        boolean validateOriginalPassword = PasswordUtil.validatePassword(request.getOriginalPassword(), adminUserEO.getSalt(), adminUserEO.getPassword());
        if (!validateOriginalPassword) {
            logger.info("原密码错误 uuid:{}", uuid);
            response.setStatus(ORIGINAL_PASSWORD_ERROR);
            return new BaseResponse(ORIGINAL_PASSWORD_ERROR, "原密码错误");
        }

        if (!request.getNewPassword().equalsIgnoreCase(request.getConfirmPassword())) {
            logger.info("新密码不一致 uuid:{}", uuid);
            response.setStatus(NEW_PASSWORD_NOT_EQUAL);
            return new BaseResponse(NEW_PASSWORD_NOT_EQUAL, "新密码不一致");
        }

        adminUserEO.setPassword(PasswordUtil.encryptPassword(request.getNewPassword(), adminUserEO.getSalt()));

        adminUserService.updatePassword(adminUserEO.getPassword(), adminUserEO.getId());

        logger.info(successUpdate + " uuid:{}", uuid);
        return successUpdateBaseResponse();
    }

    /**
     * 通过Token获取UserID
     * @param token token
     * @return userId
     */
    private String getUserIdByToken(String token) {
        String payload = JWTUtil.getPayloadStringByToken(token);
        return JsonIterator.deserialize(payload).get("userId").toString();
    }

}
