package com.ifox.platform.adminuser.rest;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.exception.RepeatedAdminUserException;
import com.ifox.platform.adminuser.request.LoginRequest;
import com.ifox.platform.adminuser.request.SaveRequest;
import com.ifox.platform.adminuser.response.AdminUserVO;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.common.rest.BaseResponse;
import com.ifox.platform.common.rest.MultiResponse;
import com.ifox.platform.common.rest.TokenResponse;
import com.ifox.platform.entity.adminuser.AdminUserEO;
import com.ifox.platform.utility.common.DigestUtil;
import com.ifox.platform.utility.common.EncodeUtil;
import com.ifox.platform.utility.common.ExceptionUtil;
import com.ifox.platform.utility.common.PasswordUtil;
import com.ifox.platform.utility.jwt.JWTHeader;
import com.ifox.platform.utility.jwt.JWTPayload;
import com.ifox.platform.utility.jwt.JWTUtil;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.ifox.platform.common.constant.RestStatusConstant.*;

/**
 * 后台用户管理接口
 */
@Api("后台用户管理接口")
@Controller
@RequestMapping(value = "/adminUser", headers = "api-version=1.0")
public class AdminUserController {

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
            e.printStackTrace();
            tokenResponse.setStatus(NOT_FOUND);
            tokenResponse.setDesc("用户不存在");
            logger.info("登陆异常 loginName:{}", loginRequest.getLoginName());
            return tokenResponse;
        }

        if (validAdminUser) {
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
        } else {
            tokenResponse.setStatus(USER_NAME_OR_PASSWORD_ERROR);
            tokenResponse.setDesc("用户名或者密码错误");
            logger.info("用户名或者密码错误 loginName:{}", loginRequest.getLoginName());
            return tokenResponse;
        }
    }

    @ApiOperation(value = "保存用户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse save(@ApiParam @RequestBody SaveRequest saveRequest, @RequestHeader("Authorization") String token){
        logger.info("保存用户信息:{}", saveRequest);
        BaseResponse baseResponse = new BaseResponse();

        String payload = JWTUtil.getPayloadStringByToken(token, env.getProperty("jwt.secret"));
        String userId = JsonIterator.deserialize(payload).get("userId").toString();

        AdminUserEO adminUserEO = new AdminUserEO();
        BeanUtils.copyProperties(saveRequest, adminUserEO);

        adminUserEO.setCreator(userId);

        byte[] bytes = DigestUtil.generateSalt(PasswordUtil.SALT_SIZE);
        String salt = EncodeUtil.encodeHex(bytes);

        adminUserEO.setSalt(salt);
        adminUserEO.setPassword(PasswordUtil.encryptPassword(saveRequest.getPassword(), salt));

        adminUserService.save(adminUserEO);

        baseResponse.setStatus(SUCCESS);
        baseResponse.setDesc("保存成功");
        logger.info("保存成功:{}", saveRequest.getLoginName());
        return baseResponse;
    }

    @ApiOperation(value = "获取所有用户信息", notes = "获取所有用户信息接口")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    MultiResponse<AdminUserVO> listAll(){
        logger.info("获取所有用户信息");
        List<AdminUserEO> adminUserEOList = adminUserService.listAll();
        List<AdminUserVO> adminUserVOList = new ArrayList<>();
        for (int i = 0; i < adminUserEOList.size(); i ++) {
            AdminUserEO adminUserEO = adminUserEOList.get(i);
            AdminUserVO adminUserVO = new AdminUserVO();
            BeanUtils.copyProperties(adminUserEO, adminUserVO);
            adminUserVOList.add(adminUserVO);
        }

        MultiResponse<AdminUserVO> multiResponse = new MultiResponse<>();
        multiResponse.setStatus(SUCCESS);
        multiResponse.setDesc("成功");
        multiResponse.setData(adminUserVOList);
        logger.info("获取成功");
        return multiResponse;
    }


}
