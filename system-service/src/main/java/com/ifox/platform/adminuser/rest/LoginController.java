package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.exception.RepeatedAdminUserException;
import com.ifox.platform.adminuser.request.adminuser.AdminUserLoginRequest;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.common.rest.response.TokenResponse;
import com.ifox.platform.entity.sys.AdminUserEO;
import com.ifox.platform.utility.common.ExceptionUtil;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.jwt.JWTHeader;
import com.ifox.platform.utility.jwt.JWTUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

import static com.ifox.platform.common.constant.RestStatusConstant.*;

@Api(tags = "后台用户登陆")
@Controller
@RequestMapping(value = "/adminUser", headers = {"api-version=1.0"})
public class LoginController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Autowired
    private AdminUserService adminUserService;


    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiResponses({ @ApiResponse(code = 200, message = "登陆成功"),
                    @ApiResponse(code = 500, message = "服务器异常"),
                    @ApiResponse(code = 404, message = "用户不存在"),
                    @ApiResponse(code = 480, message = "用户名或者密码错误"),
                    @ApiResponse(code = 483, message = "用户状态无效")})
    public @ResponseBody TokenResponse login(@ApiParam @RequestBody AdminUserLoginRequest adminUserLoginRequest){
        String uuid = UUIDUtil.randomUUID();
        logger.info("用户登陆:{}, uuid:{}", adminUserLoginRequest, uuid);
        Boolean validAdminUser;
        TokenResponse tokenResponse = new TokenResponse();
        try {
            validAdminUser = adminUserService.validLoginNameAndPassword(adminUserLoginRequest.getLoginName(), adminUserLoginRequest.getPassword());
        } catch (NotFoundAdminUserException | RepeatedAdminUserException e) {
            logger.warn(ExceptionUtil.getStackTraceAsString(e));
            tokenResponse.setStatus(NOT_FOUND);
            tokenResponse.setDesc("用户不存在");
            logger.info("登陆异常 loginName:{}, uuid:{}", adminUserLoginRequest.getLoginName(), uuid);
            return tokenResponse;
        }

        if (!validAdminUser) {
            tokenResponse.setStatus(USER_NAME_OR_PASSWORD_ERROR);
            tokenResponse.setDesc("用户名或者密码错误");
            logger.info("用户名或者密码错误 loginName:{}, uuid:{}", adminUserLoginRequest.getLoginName(), uuid);
            return tokenResponse;
        }

        AdminUserDTO adminUserDTO = adminUserService.getByLoginName(adminUserLoginRequest.getLoginName());
        if (adminUserDTO.getStatus() == AdminUserEO.AdminUserEOStatus.INVALID) {
            tokenResponse.setStatus(INVALID_STATUS);
            tokenResponse.setDesc("用户状态无效");
            logger.info("用户状态无效:uuid:{}", uuid);
            return tokenResponse;
        }

        String secret = env.getProperty("jwt.secret");
        try {
            tokenResponse.setStatus(SUCCESS);
            tokenResponse.setDesc("登陆成功");
            String token = JWTUtil.generateJWT(new JWTHeader(), adminUserService.generatePayload(adminUserLoginRequest.getLoginName()), secret);
            tokenResponse.setToken(token);
            logger.info("登陆成功 loginName:{}, token:{}, uuid:{}", adminUserLoginRequest.getLoginName(), token, uuid);
        } catch (UnsupportedEncodingException e) {
            tokenResponse.setStatus(SERVER_EXCEPTION);
            tokenResponse.setDesc("服务器异常");
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            logger.info("登陆异常 loginName:{}, uuid:{}", adminUserLoginRequest.getLoginName(), uuid);
        }
        return tokenResponse;
    }

    @ApiOperation("校验Token接口")
    @RequestMapping(value = "/verifyToken", method = RequestMethod.POST)
    @ApiResponses({ @ApiResponse(code = 200, message = "Token校验成功"),
                    @ApiResponse(code = 481, message = "Token校验失败")})
    public @ResponseBody BaseResponse verifyToken(@ApiParam String token) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("校验Token:{}, uuid:", token, uuid);
        try {
            JWTUtil.verifyToken(token, env.getProperty("jwt.secret"));
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            logger.info("Token校验失败:uuid:{}", uuid);
            return new BaseResponse(TOKEN_ERROR, "Token校验失败");
        }
        logger.info("Token校验成功:uuid:{}", uuid);
        return successBaseResponse("Token校验成功");
    }

}
