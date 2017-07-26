package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.dto.RoleDTO;
import com.ifox.platform.adminuser.request.role.RolePageRequest;
import com.ifox.platform.adminuser.request.role.RoleSaveRequest;
import com.ifox.platform.adminuser.request.role.RoleUpdateRequest;
import com.ifox.platform.adminuser.response.RoleVO;
import com.ifox.platform.adminuser.service.RoleService;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.PageInfo;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.common.rest.response.OneResponse;
import com.ifox.platform.common.rest.response.PageResponse;
import com.ifox.platform.entity.sys.RoleEO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(description = "角色管理", basePath = "/")
@Controller
@RequestMapping(value = "/role", headers = {"api-version=1.0", "Authorization"})
public class RoleController extends BaseController<RoleVO> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleService roleService;

    @ApiOperation("保存角色信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse save(@ApiParam @RequestBody RoleSaveRequest saveRequest){
        logger.info("保存角色信息:{}", saveRequest.toString());

        RoleEO roleEO = new RoleEO();
        BeanUtils.copyProperties(saveRequest, roleEO);
        roleService.save(roleEO);

        logger.info(successSave);
        return successSaveBaseResponse();
    }

    @ApiOperation("删除角色信息")
    @RequestMapping(value = "/delete/{roleId}", method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse delete(@ApiParam @PathVariable(name = "roleId") String roleId) {
        logger.info("删除角色信息:{}", roleId);

        RoleEO roleEO = roleService.get(roleId);
        if (roleEO == null) {
            logger.info("角色不存在:{}", roleId);
            return super.notFoundBaseResponse("角色不存在");
        }
        roleEO.setStatus(RoleEO.RoleEOStatus.INVALID);
        roleService.update(roleEO);

        logger.info(successDelete);
        return successDeleteBaseResponse();
    }

    @ApiOperation("更新角色信息")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse update(@ApiParam @RequestBody RoleUpdateRequest updateRequest) {
        logger.info("更新用户信息:{}", updateRequest);

        String id = updateRequest.getId();
        RoleEO roleEO = roleService.get(id);
        if (roleEO == null) {
            logger.info("角色不存在:{}", id);
            return super.notFoundBaseResponse("角色不存在");
        }
        BeanUtils.copyProperties(updateRequest, roleEO);
        roleService.update(roleEO);

        logger.info(successUpdate);
        return successUpdateBaseResponse();
    }

    @ApiOperation("查询单个角色信息")
    @RequestMapping(value = "/get/{roleId}", method = RequestMethod.PUT)
    @ResponseBody
    OneResponse get(@ApiParam @PathVariable(name = "roleId") String roleId) {
        logger.info("查询单个角色信息:{}", roleId);

        RoleEO roleEO = roleService.get(roleId);
        if (roleEO == null) {
            logger.info("角色不存在:{}", roleId);
            return super.notFoundOneResponse("角色不存在");
        }
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(roleEO, vo);

        logger.info(successQuery);
        return successQueryOneResponse(vo);
    }

    @ApiOperation(value = "分页查询角色")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    PageResponse page(@ApiParam @RequestBody RolePageRequest pageRequest) {
        logger.info("分页查询角色:{}", pageRequest);

        Page<RoleDTO> page = roleService.page(pageRequest);
        List<RoleDTO> roleDTOList = page.getContent();
        List<RoleVO> roleVOList = new ArrayList<>();
        for (RoleDTO dto :
            roleDTOList) {
            RoleVO vo = new RoleVO();
            BeanUtils.copyProperties(dto, vo);
            roleVOList.add(vo);
        }

        PageInfo pageInfo = new PageInfo(page.getTotalCount(), page.getPageSize(), page.getPageNo());

        logger.info(successQuery);
        return successQueryPageResponse(pageInfo, roleVOList);
    }

}
