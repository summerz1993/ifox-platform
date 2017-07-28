package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.adminuser.request.adminuser.AdminUserPageRequest;
import com.ifox.platform.adminuser.request.adminuser.AdminUserQueryRequest;
import com.ifox.platform.adminuser.request.adminuser.AdminUserSaveRequest;
import com.ifox.platform.adminuser.request.adminuser.AdminUserUpdateRequest;
import com.ifox.platform.adminuser.response.AdminUserVO;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.rest.*;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.common.rest.response.MultiResponse;
import com.ifox.platform.common.rest.response.OneResponse;
import com.ifox.platform.common.rest.response.PageResponse;
import com.ifox.platform.entity.sys.AdminUserEO;
import com.ifox.platform.utility.common.DigestUtil;
import com.ifox.platform.utility.common.EncodeUtil;
import com.ifox.platform.utility.common.PasswordUtil;
import com.ifox.platform.utility.jwt.JWTPayload;
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
public class AdminUserController extends BaseController<AdminUserVO> {

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

        logger.info(successSave);
        return successSaveBaseResponse();
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse delete(@ApiParam @PathVariable(name = "userId") String userId){
        logger.info("删除用户:{}", userId);

        AdminUserEO eo = adminUserService.get(userId);
        if (eo == null) {
            logger.info("此用户不存在");
            return super.notFoundBaseResponse("此用户不存在");
        }

        adminUserService.deleteByEntity(eo);

        logger.info(successDelete);
        return successDeleteBaseResponse();
    }

    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse update(@ApiParam @RequestBody AdminUserUpdateRequest updateRequest){
        logger.info("更新用户信息:{}", updateRequest.toString());

        String id = updateRequest.getId();
        AdminUserEO adminUserEO = adminUserService.get(id);
        if (adminUserEO == null) {
            logger.info("此用户不存在");
            return super.notFoundBaseResponse("此用户不存在");
        }
        BeanUtils.copyProperties(updateRequest, adminUserEO);
        adminUserEO.setPassword(PasswordUtil.encryptPassword(updateRequest.getPassword(), adminUserEO.getSalt()));
        adminUserService.update(adminUserEO);

        logger.info(successUpdate);
        return successUpdateBaseResponse();
    }

    @ApiOperation(value = "单个用户信息查询")
    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
    @ResponseBody
    @SuppressWarnings("unchecked")
    OneResponse<AdminUserVO> get(@ApiParam @PathVariable(name = "userId") String userId){
        logger.info("单个用户信息查询:{}", userId);

        AdminUserEO eo = adminUserService.get(userId);
        if (eo == null) {
            logger.info("此用户不存在");
            return super.notFoundOneResponse("此用户不存在");
        }

        AdminUserVO vo = new AdminUserVO();
        BeanUtils.copyProperties(eo, vo);

        logger.info(successQuery);
        return successQueryOneResponse(vo);
    }

    @ApiOperation(value = "分页查询用户")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("unchecked")
    PageResponse<AdminUserVO> page(@ApiParam @RequestBody AdminUserPageRequest pageRequest) {
        logger.info("分页查询用户:{} {}", pageRequest.toString());

        Page<AdminUserDTO> page = adminUserService.page(pageRequest);
        List<AdminUserDTO> adminUserDTOList = page.getContent();
        List<AdminUserVO> adminUserVOList = new ArrayList<>();
        for (AdminUserDTO dto :
            adminUserDTOList) {
            AdminUserVO vo = new AdminUserVO();
            BeanUtils.copyProperties(dto, vo);
            adminUserVOList.add(vo);
        }

        PageInfo pageInfo = new PageInfo(page.getTotalCount(), page.getPageSize(), page.getPageNo());

        logger.info(successQuery);
        return successQueryPageResponse(pageInfo, adminUserVOList);
    }

    @ApiOperation(value = "获取所有用户信息", notes = "获取所有用户信息接口")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("unchecked")
    MultiResponse<AdminUserVO> list(@ApiParam @RequestBody AdminUserQueryRequest queryRequest){
        logger.info("获取多条用户信息:{}", queryRequest.toString());

        List<AdminUserDTO> adminUserDTOList = adminUserService.list(queryRequest);

        List<AdminUserVO> adminUserVOList = new ArrayList<>();
        for (int i = 0; i < adminUserDTOList.size(); i ++) {
            AdminUserDTO adminUserDTO = adminUserDTOList.get(i);
            AdminUserVO adminUserVO = new AdminUserVO();
            BeanUtils.copyProperties(adminUserDTO, adminUserVO);
            adminUserVOList.add(adminUserVO);
        }

        logger.info(successQuery);
        return successQueryMultiResponse(adminUserVOList);
    }

}
