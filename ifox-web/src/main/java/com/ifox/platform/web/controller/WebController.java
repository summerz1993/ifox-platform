package com.ifox.platform.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请求控制器
 * Created by yezhang on 7/13/2017.
 */
@Api(value = "测试控制器")
@Controller
@RequestMapping("/web")
public class WebController {

    @ApiOperation(value = "登陆", notes = "根据name登陆")
    @ApiImplicitParam(name = "name", value = "登陆名", required = true, dataType = "String")
    @RequestMapping(value = "/login", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    String login(String name){
        return "登陆成功";
    }

}
