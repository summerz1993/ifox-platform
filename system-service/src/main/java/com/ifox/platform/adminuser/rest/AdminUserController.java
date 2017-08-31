package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.adminuser.request.adminuser.AdminUserPageRequest;
import com.ifox.platform.adminuser.request.adminuser.AdminUserQueryRequest;
import com.ifox.platform.adminuser.request.adminuser.AdminUserSaveRequest;
import com.ifox.platform.adminuser.request.adminuser.AdminUserUpdateRequest;
import com.ifox.platform.adminuser.response.AdminUserVO;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.PageInfo;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.common.rest.response.MultiResponse;
import com.ifox.platform.common.rest.response.OneResponse;
import com.ifox.platform.common.rest.response.PageResponse;
import com.ifox.platform.entity.sys.AdminUserEO;
import com.ifox.platform.utility.common.DigestUtil;
import com.ifox.platform.utility.common.EncodeUtil;
import com.ifox.platform.utility.common.PasswordUtil;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.jwt.JWTUtil;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import com.jsoniter.JsonIterator;
import io.swagger.annotations.*;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static com.ifox.platform.common.constant.RestStatusConstant.EXISTED_LOGIN_NAME;

@Api(tags = "后台用户管理")
@Controller
@RequestMapping(value = "/adminUser", headers = {"api-version=1.0", "Authorization"})
public class AdminUserController extends BaseController<AdminUserVO> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Autowired
    private AdminUserService adminUserService;


    @ApiOperation("保存用户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({ @ApiResponse(code = 701, message = "登录名已经存在")})
    public @ResponseBody BaseResponse save(@ApiParam @RequestBody AdminUserSaveRequest adminUserSaveRequest, @RequestHeader("Authorization") String token){
        String uuid = UUIDUtil.randomUUID();
        logger.info("保存用户信息 adminUserSaveRequest:{}, uuid:{}", adminUserSaveRequest, uuid);

        String loginName = adminUserSaveRequest.getLoginName();
        AdminUserDTO byLoginName = adminUserService.getByLoginName(loginName);
        if (byLoginName != null) {
            logger.info("登录名已经存在 uuid:{}", uuid);
            return new BaseResponse(EXISTED_LOGIN_NAME, "登录名已经存在");
        }

        String payload = JWTUtil.getPayloadStringByToken(token, env.getProperty("jwt.secret"));
        String userId = JsonIterator.deserialize(payload).get("userId").toString();

        AdminUserEO adminUserEO = ModelMapperUtil.get().map(adminUserSaveRequest, AdminUserEO.class);

        adminUserEO.setCreator(userId);

        byte[] bytes = DigestUtil.generateSalt(PasswordUtil.SALT_SIZE);
        String salt = EncodeUtil.encodeHex(bytes);

        adminUserEO.setSalt(salt);
        adminUserEO.setPassword(PasswordUtil.encryptPassword(adminUserSaveRequest.getPassword(), salt));

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

        String payload = JWTUtil.getPayloadStringByToken(token, env.getProperty("jwt.secret"));
        String userId = JsonIterator.deserialize(payload).get("userId").toString();
        if (Arrays.asList(ids).contains(userId)) {
            logger.info("不允许删除自身账号 userId:{}, uuid:{}", userId, uuid);
            return deleteSelfErrorBaseResponse(response);
        }

        try {
            adminUserService.deleteMulti(ids);
        } catch (IllegalArgumentException e) {
            logger.info("用户不存在 uuid:{}", uuid);
            return notFoundBaseResponse("用户不存在", response);
        }

        logger.info(successDelete + " uuid:{}", uuid);
        return successDeleteBaseResponse();
    }

    @ApiOperation("更新用户")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiResponses({ @ApiResponse(code = 404, message = "用户不存在"),
                    @ApiResponse(code = 701, message = "登录名已经存在")})
    public @ResponseBody BaseResponse update(@ApiParam @RequestBody AdminUserUpdateRequest updateRequest, HttpServletResponse response){
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
            AdminUserDTO byLoginName = adminUserService.getByLoginName(loginName);
            if (byLoginName != null) {
                logger.info("登录名已经存在 uuid:{}" ,uuid);
                return new BaseResponse(EXISTED_LOGIN_NAME, "登录名已经存在");
            }
        }

        ModelMapperUtil.get().map(updateRequest, adminUserEO);
//        adminUserEO.setPassword(PasswordUtil.encryptPassword(updateRequest.getPassword(), adminUserEO.getSalt()));
        adminUserService.update(adminUserEO);

        logger.info(successUpdate + " uuid:{}", uuid);
        return successUpdateBaseResponse();
    }

    @ApiOperation("单个用户信息查询")
    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 404, message = "此用户不存在") })
    @SuppressWarnings("unchecked")
    public @ResponseBody OneResponse<AdminUserVO> get(@ApiParam @PathVariable(name = "userId") String userId, HttpServletResponse response){
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

        Page<AdminUserDTO> page = adminUserService.page(pageRequest);
        List<AdminUserDTO> adminUserDTOList = page.getContent();

        List<AdminUserVO> adminUserVOList = ModelMapperUtil.get().map(adminUserDTOList, new TypeToken<List<AdminUserVO>>() {}.getType());

        PageInfo pageInfo = page.convertPageInfo();

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryPageResponse(pageInfo, adminUserVOList);
    }

    @ApiOperation("获取用户列表接口")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public MultiResponse<AdminUserVO> list(@ApiParam @RequestBody AdminUserQueryRequest queryRequest){
        String uuid = UUIDUtil.randomUUID();
        logger.info("获取用户列表 queryRequest:{}, uuid:{}", queryRequest.toString(), uuid);

        List<AdminUserDTO> adminUserDTOList = adminUserService.list(queryRequest);
        List<AdminUserVO> adminUserVOList = ModelMapperUtil.get().map(adminUserDTOList, new TypeToken<List<AdminUserVO>>() {}.getType());

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryMultiResponse(adminUserVOList);
    }

    public void changePassword() {

    }

}
