package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.exception.RepeatedAdminUserException;
import com.ifox.platform.adminuser.request.LoginRequest;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.common.rest.TokenResponse;
import com.ifox.platform.utility.common.ExceptionUtil;
import com.ifox.platform.utility.jwt.JWTHeader;
import com.ifox.platform.utility.jwt.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

import static com.ifox.platform.common.constant.RestStatusConstant.*;

@Api(description = "后台用户登陆", basePath = "/")
@Controller
@RequestMapping(value = "/adminUser", headers = {"api-version=1.0"})
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Autowired
    private AdminUserService adminUserService;


    @ApiOperation(value = "后台用户登录", notes = "后台用户登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    TokenResponse login(@ApiParam @RequestBody LoginRequest loginRequest){
        logger.info("用户登陆:{}", loginRequest);
        Boolean validAdminUser = false;
        TokenResponse tokenResponse = new TokenResponse();
        try {
            validAdminUser = adminUserService.validLoginNameAndPassword(loginRequest.getLoginName(), loginRequest.getPassword());
        } catch (NotFoundAdminUserException | RepeatedAdminUserException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            tokenResponse.setStatus(NOT_FOUND);
            tokenResponse.setDesc("用户不存在");
            logger.info("登陆异常 loginName:{}", loginRequest.getLoginName());
            return tokenResponse;
        }

        if (!validAdminUser) {
            tokenResponse.setStatus(USER_NAME_OR_PASSWORD_ERROR);
            tokenResponse.setDesc("用户名或者密码错误");
            logger.info("用户名或者密码错误 loginName:{}", loginRequest.getLoginName());
            return tokenResponse;
        }

        String secret = env.getProperty("jwt.secret");
        try {
            tokenResponse.setStatus(SUCCESS);
            tokenResponse.setDesc("登陆成功");
            String token = JWTUtil.generateJWT(new JWTHeader(), adminUserService.generatePayload(loginRequest.getLoginName()), secret);
            tokenResponse.setToken(token);
            logger.info("登陆成功 loginName:{}, token:{}", loginRequest.getLoginName(), token);
        } catch (UnsupportedEncodingException e) {
            tokenResponse.setStatus(SERVER_EXCEPTION);
            tokenResponse.setDesc("服务器异常");
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            logger.info("登陆异常 loginName:{}", loginRequest.getLoginName());
        }
        return tokenResponse;
    }

}
