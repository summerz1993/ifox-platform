package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.request.AdminUserQueryRequest;
import com.ifox.platform.adminuser.request.AdminUserSaveRequest;
import com.ifox.platform.adminuser.response.AdminUserVO;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.common.rest.BaseResponse;
import com.ifox.platform.common.rest.MultiResponse;
import com.ifox.platform.common.rest.PageRequest;
import com.ifox.platform.entity.adminuser.AdminUserEO;
import com.ifox.platform.utility.common.DigestUtil;
import com.ifox.platform.utility.common.EncodeUtil;
import com.ifox.platform.utility.common.PasswordUtil;
import com.ifox.platform.utility.jwt.JWTUtil;
import com.jsoniter.JsonIterator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

@Api(description = "后台用户管理", basePath = "/")
@Controller
@RequestMapping(value = "/adminUser", headers = {"api-version=1.0", "Authorization"})
public class AdminUserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Autowired
    private AdminUserService adminUserService;


    @ApiOperation(value = "保存用户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse save(@ApiParam @RequestBody AdminUserSaveRequest adminUserSaveRequest, @RequestHeader("Authorization") String token){
        logger.info("保存用户信息:{}", adminUserSaveRequest);
        BaseResponse baseResponse = new BaseResponse();

        String payload = JWTUtil.getPayloadStringByToken(token, env.getProperty("jwt.secret"));
        String userId = JsonIterator.deserialize(payload).get("userId").toString();

        AdminUserEO adminUserEO = new AdminUserEO();
        BeanUtils.copyProperties(adminUserSaveRequest, adminUserEO);

        adminUserEO.setCreator(userId);

        byte[] bytes = DigestUtil.generateSalt(PasswordUtil.SALT_SIZE);
        String salt = EncodeUtil.encodeHex(bytes);

        adminUserEO.setSalt(salt);
        adminUserEO.setPassword(PasswordUtil.encryptPassword(adminUserSaveRequest.getPassword(), salt));

        adminUserService.save(adminUserEO);

        baseResponse.setStatus(SUCCESS);
        baseResponse.setDesc("保存成功");
        logger.info("保存成功:{}", adminUserSaveRequest.getLoginName());
        return baseResponse;
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse delete(@ApiParam @RequestBody String[] userIds){
        logger.info("删除用户:{}", userIds);
        BaseResponse baseResponse = new BaseResponse();

        adminUserService.deleteMulti(userIds);

        baseResponse.setStatus(SUCCESS);
        baseResponse.setDesc("删除成功");
        logger.info("删除成功");
        return baseResponse;
    }

    @ApiOperation(value = "分页查询用户")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    BaseResponse page(@ApiParam @RequestBody AdminUserQueryRequest adminUserQueryRequest, @ApiParam @RequestBody PageRequest pageRequest) {
        logger.info("分页查询用户:{} {}", adminUserQueryRequest.toString(), pageRequest.toString());
        BaseResponse baseResponse = new BaseResponse();

        SimplePage simplePage = new SimplePage();
        simplePage.setPageNo(pageRequest.getPageNo());
        simplePage.setPageSize(pageRequest.getPageSize());

        List<QueryProperty> queryPropertyList = new ArrayList<>();

        String loginName = adminUserQueryRequest.getLoginName();
        if (!StringUtils.isEmpty(loginName)) {
            QueryProperty queryLoginName = new QueryProperty("loginName", EnumDao.Operation.LIKE, loginName);
            queryPropertyList.add(queryLoginName);
        }

        AdminUserEO.AdminUserEOStatus status = adminUserQueryRequest.getStatus();
        if (status != null) {
            QueryProperty queryStatus = new QueryProperty("status", EnumDao.Operation.EQUAL, status);
            queryPropertyList.add(queryStatus);
        }

        Boolean buildinSystem = adminUserQueryRequest.getBuildinSystem();
        if (buildinSystem != null) {
            QueryProperty queryBuildinSystem = new QueryProperty("buildinSystem", EnumDao.Operation.EQUAL, buildinSystem);
            queryPropertyList.add(queryBuildinSystem);
        }

        Page<AdminUserEO> adminUserEOPage = adminUserService.pageByQueryProperty(simplePage, queryPropertyList);


        baseResponse.setStatus(SUCCESS);
        baseResponse.setDesc("分页查询成功");
        logger.info("分页查询用户成功");
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
        multiResponse.setDesc("成功获取所有用户信息");
        multiResponse.setData(adminUserVOList);
        logger.info("获取成功");
        return multiResponse;
    }


}
