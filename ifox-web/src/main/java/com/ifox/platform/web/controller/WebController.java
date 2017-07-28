package com.ifox.platform.web.controller;

import com.github.kevinsawicki.http.HttpRequest;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String home(String token, Model model){
        logger.info("进入主页");

        String url = env.getProperty("ifox-web.admin-user-service-base-url") + "adminUser/verifyToken";
        HttpRequest httpRequest = HttpRequest.post(url).header("api-version", "1.0").form("token", token);
        int code = httpRequest.code();
        String body = httpRequest.body();
        logger.info("code = {}", code);
        logger.info("body = {}", body);

        Any any = JsonIterator.deserialize(body);
        int status = any.get("status").toInt();

        if ( SUCCESS == code && SUCCESS == status) {
            //token校验通过
            logger.info("token校验通过");
            return "/home";
        } else {
            logger.info("token校验失败");
            model.addAttribute("error", "无效请求");
            return "/error";
        }

    }

}
