package com.ifox.platform.web.controller;

import com.ifox.platform.web.controller.base.BaseWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

@Controller
@RequestMapping("/web")
public class WebController extends BaseWebController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        logger.info("登陆");
        return "/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public @ResponseBody Integer logout(String token){
        logger.info("登出, token:{}", token);
        //TODO:销毁token

        return SUCCESS;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(HttpServletRequest request, Model model) {
        logger.info("进入主页");

        return verifyToken(request, model, "/home");
    }

    @RequestMapping(value = "/adminUser", method = RequestMethod.GET)
    public String adminUser(HttpServletRequest request, Model model){
        logger.info("进入后台用户管理");

        return verifyToken(request, model, "/admin-user/main");
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public String role(HttpServletRequest request, Model model){
        logger.info("进入角色管理");

        return verifyToken(request, model, "/role/main");
    }

    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    public String resource(HttpServletRequest request, Model model){
        logger.info("进入后台资源管理");

        return verifyToken(request, model, "/resource/main");
    }
}
