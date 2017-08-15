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
    public @ResponseBody BaseResponse save(@ApiParam @RequestBody AdminUserSaveRequest adminUserSaveRequest, @RequestHeader("Authorization") String token){
        logger.info("保存用户信息:{}", adminUserSaveRequest);

        String loginName = adminUserSaveRequest.getLoginName();
        AdminUserDTO byLoginName = adminUserService.getByLoginName(loginName);
        if (byLoginName != null) {
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

        logger.info(successSave);
        return successSaveBaseResponse();
    }

    @ApiOperation("删除用户")
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    @ApiResponses({ @ApiResponse(code = 404, message = "此用户不存在") })
    public @ResponseBody BaseResponse delete(@ApiParam @PathVariable(name = "userId") String userId){
        logger.info("删除用户:{}", userId);

        AdminUserEO eo = adminUserService.get(userId);
        if (eo == null) {
            logger.info("此用户不存在");
            return super.notFoundBaseResponse("此用户不存在");
        }

        adminUserService.deleteByEntity(eo);

        logger.info(successDelete);
        return successDeleteBaseResponse();
    }

    @ApiOperation("更新用户")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiResponses({ @ApiResponse(code = 404, message = "此用户不存在"),
                    @ApiResponse(code = 482, message = "登录名已经存在")})
    public @ResponseBody BaseResponse update(@ApiParam @RequestBody AdminUserUpdateRequest updateRequest){
        logger.info("更新用户信息:{}", updateRequest.toString());

        String id = updateRequest.getId();
        AdminUserEO adminUserEO = adminUserService.get(id);
        if (adminUserEO == null) {
            logger.info("此用户不存在");
            return super.notFoundBaseResponse("此用户不存在");
        }
        String loginName = updateRequest.getLoginName();
        AdminUserDTO byLoginName = adminUserService.getByLoginName(loginName);
        if (byLoginName != null) {
            return new BaseResponse(EXISTED_LOGIN_NAME, "登录名已经存在");
        }
        ModelMapperUtil.get().map(updateRequest, adminUserEO);
//        adminUserEO.setPassword(PasswordUtil.encryptPassword(updateRequest.getPassword(), adminUserEO.getSalt()));
        adminUserService.update(adminUserEO);

        logger.info(successUpdate);
        return successUpdateBaseResponse();
    }

    @ApiOperation("单个用户信息查询")
    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 404, message = "此用户不存在") })
    @SuppressWarnings("unchecked")
    public @ResponseBody OneResponse<AdminUserVO> get(@ApiParam @PathVariable(name = "userId") String userId){
        logger.info("单个用户信息查询:{}", userId);

        AdminUserEO eo = adminUserService.get(userId);
        if (eo == null) {
            logger.info("此用户不存在");
            return super.notFoundOneResponse("此用户不存在");
        }

        AdminUserVO vo = new AdminUserVO();
        ModelMapperUtil.get().map(eo, vo);

        logger.info(successQuery);
        return successQueryOneResponse(vo);
    }

    @ApiOperation("分页查询用户")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public PageResponse<AdminUserVO> page(@ApiParam @RequestBody AdminUserPageRequest pageRequest) {
        logger.info("分页查询用户:{}", pageRequest.toString());

        Page<AdminUserDTO> page = adminUserService.page(pageRequest);
        List<AdminUserDTO> adminUserDTOList = page.getContent();

        List<AdminUserVO> adminUserVOList = ModelMapperUtil.get().map(adminUserDTOList, new TypeToken<List<AdminUserVO>>() {}.getType());

        PageInfo pageInfo = page.convertPageInfo();

        logger.info(successQuery);
        return successQueryPageResponse(pageInfo, adminUserVOList);
    }

    @ApiOperation("获取所有用户信息接口")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public MultiResponse<AdminUserVO> list(@ApiParam @RequestBody AdminUserQueryRequest queryRequest){
        logger.info("获取多条用户信息:{}", queryRequest.toString());

        List<AdminUserDTO> adminUserDTOList = adminUserService.list(queryRequest);
        List<AdminUserVO> adminUserVOList = ModelMapperUtil.get().map(adminUserDTOList, new TypeToken<List<AdminUserVO>>() {}.getType());

        logger.info(successQuery);
        return successQueryMultiResponse(adminUserVOList);
    }

}
