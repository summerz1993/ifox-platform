package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.request.LoginRequest;
import com.ifox.platform.adminuser.request.SaveRequest;
import com.ifox.platform.adminuser.response.AdminUserVO;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.common.rest.BaseResponse;
import com.ifox.platform.common.rest.MultiResponse;
import com.ifox.platform.common.rest.TokenResponse;
import com.ifox.platform.entity.adminuser.AdminUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

/**
 * 后台用户管理接口
 * Created by yezhang on 7/14/2017.
 */
@Api("后台用户管理接口")
@Controller
@RequestMapping("/adminUser")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @ApiOperation(value = "后台用户登录", notes = "后台用户登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    TokenResponse login(@ApiParam @RequestBody LoginRequest loginRequest){
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setStatus(SUCCESS);
        tokenResponse.setDesc("成功");
        tokenResponse.setJwt("woefjweof29037r02934ru20rfr4f");
        return tokenResponse;
    }

    @ApiOperation(value = "保存用户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse save(@ApiParam @RequestBody SaveRequest saveRequest){
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(saveRequest, adminUser);
        adminUserService.save(adminUser);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(SUCCESS);
        baseResponse.setDesc("成功");
        return baseResponse;
    }

    @ApiOperation(value = "获取所有用户信息", notes = "获取所有用户信息接口")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    MultiResponse<AdminUserVO> listAll(){
        List<AdminUser> adminUserList = adminUserService.listAll();

        List<AdminUserVO> adminUserVOList = new ArrayList<>();
        for (int i = 0; i < adminUserList.size(); i ++) {
            AdminUser adminUser = adminUserList.get(i);
            AdminUserVO adminUserVO = new AdminUserVO();
            BeanUtils.copyProperties(adminUser, adminUserVO);
            adminUserVOList.add(adminUserVO);
        }

        MultiResponse<AdminUserVO> multiResponse = new MultiResponse<>();
        multiResponse.setStatus(SUCCESS);
        multiResponse.setDesc("成功");
        multiResponse.setData(adminUserVOList);
        return multiResponse;
    }

}
