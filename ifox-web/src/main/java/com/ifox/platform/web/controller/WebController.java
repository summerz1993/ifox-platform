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
    public ModelAndView login(){
        logger.info("进入登录页面");

        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("test", "yeager test");
        return modelAndView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(String token){
        //TODO:token校验
        return "/home";
    }

}
