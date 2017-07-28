package com.ifox.platform.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/web")
public class WebController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        logger.info("进入登录页面");
        return "/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(String token){
        logger.info("进入主页");
        //TODO:token校验
        return "/home";
    }

    @RequestMapping(value = "/adminUserList", method = RequestMethod.GET)
    public String adminUserList(String token){
        logger.info("进入用户列表页面");
        //TODO:token校验
        return "/adminUser/adminUserList";
    }

}
