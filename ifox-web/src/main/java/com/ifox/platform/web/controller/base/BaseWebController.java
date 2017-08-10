package com.ifox.platform.web.controller.base;

import com.github.kevinsawicki.http.HttpRequest;
import com.ifox.platform.utility.common.ExceptionUtil;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;

import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

public class BaseWebController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    protected String verifyToken(String token, Model model, String view) {
        HttpRequest httpRequest = getTokenHttpRequest(token);
        int code = httpRequest.code();
        String body = httpRequest.body();
        logger.info("code = {}", code);
        logger.info("body = {}", body);

        Any bodyAny = JsonIterator.deserialize(body);
        int status = bodyAny.get("status").toInt();

        if ( SUCCESS != code || SUCCESS != status) {
            logger.info("token校验失败");
            model.addAttribute("error", "无效请求");
            model.addAttribute("URL", env.getProperty("ifox-web.login-url"));
            model.addAttribute("URLName", "请重新登陆");
            return "/error";
        }

        //token校验通过
        logger.info("token校验成功");

        String payload = getPayload(token);

        Any payLoadAny = JsonIterator.deserialize(payload);
        String userId = payLoadAny.get("userId").toString();
        String loginName = payLoadAny.get("loginName").toString();

        model.addAttribute("userId", userId);
        model.addAttribute("loginName", loginName);

        return view;
    }

    private String getPayload(String token) {
        String[] split = token.split("\\.");
        String payLoadString = split[1];
        byte[] bytes = Base64.decodeBase64(payLoadString);
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            return "";
        }
    }

    private HttpRequest getTokenHttpRequest(String token) {
        String verifyTokenUrl = "adminUser/verifyToken";

        String url = env.getProperty("ifox-web.admin-user-service-base-url") + verifyTokenUrl;
        return HttpRequest.post(url).header("api-version", "1.0").form("token", token);
    }

}