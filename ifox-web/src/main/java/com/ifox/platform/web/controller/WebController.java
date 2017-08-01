package com.ifox.platform.web.controller;

import com.github.kevinsawicki.http.HttpRequest;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

@Controller
@RequestMapping("/web")
public class WebController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        logger.info("进入登录页面");
        return "/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(String token, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("进入主页");

        String verifyTokenUrl = "adminUser/verifyToken";

        String url = env.getProperty("ifox-web.admin-user-service-base-url") + verifyTokenUrl;
        HttpRequest httpRequest = HttpRequest.post(url).header("api-version", "1.0").form("token", token);
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
        logger.info("token校验通过");

        String[] split = token.split("\\.");
        String payLoadString = split[1];
        byte[] bytes = Base64.decodeBase64(payLoadString);
        String payload = new String(bytes, "UTF-8");

        Any payLoadAny = JsonIterator.deserialize(payload);
        String userId = payLoadAny.get("userId").toString();
        String loginName = payLoadAny.get("loginName").toString();

        model.addAttribute("userId", userId);
        model.addAttribute("loginName", loginName);

        return "/home";

    }

    @RequestMapping(value = "/adminUserList", method = RequestMethod.GET)
    public String adminUserList(String token){
        logger.info("进入用户列表页面");
        //TODO:token校验
        return "/adminUser/list";
    }

}
