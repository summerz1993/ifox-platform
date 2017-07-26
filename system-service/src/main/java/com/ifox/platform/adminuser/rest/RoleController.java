package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.service.RoleService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(description = "角色管理", basePath = "/")
@Controller
@RequestMapping(value = "/role", headers = {"api-version=1.0", "Authorization"})
public class RoleController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleService roleService;




}
