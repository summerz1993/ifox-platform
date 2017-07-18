package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.request.LoginRequest;
import com.ifox.platform.adminuser.request.SaveRequest;
import com.ifox.platform.adminuser.response.AdminUserVO;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.common.rest.BaseResponse;
import com.ifox.platform.common.rest.MultiResponse;
import com.ifox.platform.common.rest.TokenResponse;
import com.ifox.platform.entity.adminuser.AdminUserEO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/adminUser", headers = "api-version=1.0")
public class AdminUserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdminUserService adminUserService;

    @ApiOperation(value = "后台用户登录", notes = "后台用户登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    TokenResponse login(@ApiParam @RequestBody LoginRequest loginRequest){
        logger.info("用户登陆:{}", loginRequest);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setStatus(SUCCESS);
        tokenResponse.setDesc("成功");
        tokenResponse.setJwt("woefjweof29037r02934ru20rfr4f");
        logger.info("登陆成功:{}", loginRequest.getLoginName());
        return tokenResponse;
    }

    @ApiOperation(value = "保存用户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse save(@ApiParam @RequestBody SaveRequest saveRequest){
        logger.info("保存用户信息:{}", saveRequest);
        AdminUserEO adminUserEO = new AdminUserEO();
        BeanUtils.copyProperties(saveRequest, adminUserEO);
        adminUserService.save(adminUserEO);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(SUCCESS);
        baseResponse.setDesc("成功");
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
