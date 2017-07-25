package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.adminuser.request.AdminUserQueryRequest;
import com.ifox.platform.adminuser.request.AdminUserSaveRequest;
import com.ifox.platform.adminuser.request.AdminUserUpdateRequest;
import com.ifox.platform.adminuser.response.AdminUserVO;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.rest.*;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

@Api(description = "后台用户管理", basePath = "/")
@Controller
@RequestMapping(value = "/adminUser", headers = {"api-version=1.0", "Authorization"})
public class AdminUserController extends BaseController {

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
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse delete(@ApiParam @PathVariable(name = "userId") String userId){
        logger.info("删除用户:{}", userId);
        BaseResponse baseResponse = new BaseResponse();
        AdminUserEO eo = adminUserService.get(userId);
        if (eo == null) {
            logger.info("此用户不存在");
            return super.notFoundBaseResponse("此用户不存在");
        }

        adminUserService.deleteByEntity(eo);

        baseResponse.setStatus(SUCCESS);
        baseResponse.setDesc("删除成功");
        logger.info("删除成功");
        return baseResponse;
    }

    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse update(@ApiParam @RequestBody AdminUserUpdateRequest updateRequest){
        BaseResponse baseResponse = new BaseResponse();
        String id = updateRequest.getId();
        AdminUserEO adminUserEO = adminUserService.get(id);
        if (adminUserEO == null) {
            logger.info("此用户不存在");
            return super.notFoundBaseResponse("此用户不存在");
        }
        BeanUtils.copyProperties(updateRequest, adminUserEO);
        adminUserEO.setPassword(PasswordUtil.encryptPassword(updateRequest.getPassword(), adminUserEO.getSalt()));
        adminUserService.update(adminUserEO);

        baseResponse.setStatus(SUCCESS);
        baseResponse.setDesc("更新成功");
        return baseResponse;
    }

    @ApiOperation(value = "单个用户信息查询")
    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
    @ResponseBody
    @SuppressWarnings("unchecked")
    OneResponse<AdminUserVO> get(@ApiParam @PathVariable(name = "userId") String userId){
        logger.info("单个用户信息查询:{}", userId);
        OneResponse<AdminUserVO> oneResponse = new OneResponse<>();
        AdminUserEO eo = adminUserService.get(userId);
        if (eo == null) {
            logger.info("此用户不存在");
            return super.notFoundOneResponse("此用户不存在");
        }

        AdminUserVO vo = new AdminUserVO();
        BeanUtils.copyProperties(eo, vo);

        oneResponse.setData(vo);
        oneResponse.setStatus(SUCCESS);
        oneResponse.setDesc("单个用户信息查询成功");
        logger.info("单个用户信息查询成功");
        return oneResponse;
    }

    @ApiOperation(value = "分页查询用户")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    PageResponse<AdminUserVO> page(@ApiParam @RequestBody AdminUserQueryRequest queryRequest, @ApiParam @RequestBody PageRequest pageRequest) {
        logger.info("分页查询用户:{} {}", queryRequest.toString(), pageRequest.toString());
        PageResponse<AdminUserVO> pageResponse = new PageResponse<>();

        Page<AdminUserDTO> page = adminUserService.page(queryRequest, pageRequest);
        List<AdminUserDTO> adminUserDTOList = page.getContent();
        List<AdminUserVO> adminUserVOList = new ArrayList<>();
        for (AdminUserDTO dto :
            adminUserDTOList) {
            AdminUserVO vo = new AdminUserVO();
            BeanUtils.copyProperties(dto, vo);
            adminUserVOList.add(vo);
        }

        pageResponse.setPageInfo(new PageInfo(page.getTotalCount(), page.getPageSize(), page.getPageNo()));
        pageResponse.setData(adminUserVOList);
        pageResponse.setStatus(SUCCESS);
        pageResponse.setDesc("分页查询成功");
        logger.info("分页查询用户成功");
        return pageResponse;
    }

    @ApiOperation(value = "获取所有用户信息", notes = "获取所有用户信息接口")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    MultiResponse<AdminUserVO> list(@ApiParam @RequestBody AdminUserQueryRequest queryRequest){
        logger.info("获取所有用户信息:{}", queryRequest.toString());

        List<AdminUserDTO> adminUserDTOList = adminUserService.list(queryRequest);

        List<AdminUserVO> adminUserVOList = new ArrayList<>();
        for (int i = 0; i < adminUserDTOList.size(); i ++) {
            AdminUserDTO adminUserDTO = adminUserDTOList.get(i);
            AdminUserVO adminUserVO = new AdminUserVO();
            BeanUtils.copyProperties(adminUserDTO, adminUserVO);
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
