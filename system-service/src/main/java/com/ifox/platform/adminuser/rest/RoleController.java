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
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import io.swagger.annotations.*;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@Controller
@RequestMapping(value = "/role", headers = {"api-version=1.0", "Authorization"})
public class RoleController extends BaseController<RoleVO> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleService roleService;

    @ApiOperation("保存角色信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody BaseResponse save(@ApiParam @RequestBody RoleSaveRequest saveRequest){
        logger.info("保存角色信息:{}", saveRequest.toString());

        RoleEO roleEO = ModelMapperUtil.get().map(saveRequest, RoleEO.class);
        roleService.save(roleEO);

        logger.info(successSave);
        return successSaveBaseResponse();
    }

    @ApiOperation("删除角色信息")
    @RequestMapping(value = "/delete/{roleId}", method = RequestMethod.DELETE)
    @ApiResponses({ @ApiResponse(code = 404, message = "角色不存在") })
    public @ResponseBody BaseResponse delete(@ApiParam @PathVariable(name = "roleId") String roleId) {
        logger.info("删除角色信息:{}", roleId);

        RoleEO roleEO = roleService.get(roleId);
        if (roleEO == null) {
            logger.info("角色不存在:{}", roleId);
            return super.notFoundBaseResponse("角色不存在");
        }
        roleService.deleteByEntity(roleEO);

        logger.info(successDelete);
        return successDeleteBaseResponse();
    }

    @ApiOperation("更新角色信息")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiResponses({ @ApiResponse(code = 404, message = "角色不存在") })
    public @ResponseBody BaseResponse update(@ApiParam @RequestBody RoleUpdateRequest updateRequest) {
        logger.info("更新用户信息:{}", updateRequest);

        String id = updateRequest.getId();
        RoleEO roleEO = roleService.get(id);
        if (roleEO == null) {
            logger.info("角色不存在:{}", id);
            return super.notFoundBaseResponse("角色不存在");
        }
        ModelMapperUtil.get().map(updateRequest, roleEO);
        roleService.update(roleEO);

        logger.info(successUpdate);
        return successUpdateBaseResponse();
    }

    @ApiOperation("查询单个角色信息")
    @RequestMapping(value = "/get/{roleId}", method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 404, message = "角色不存在") })
    public @ResponseBody OneResponse get(@ApiParam @PathVariable(name = "roleId") String roleId) {
        logger.info("查询单个角色信息:{}", roleId);

        RoleEO roleEO = roleService.get(roleId);
        if (roleEO == null) {
            logger.info("角色不存在:{}", roleId);
            return super.notFoundOneResponse("角色不存在");
        }
        RoleVO vo = ModelMapperUtil.get().map(roleEO, RoleVO.class);

        logger.info(successQuery);
        return successQueryOneResponse(vo);
    }

    @ApiOperation("分页查询角色")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public @ResponseBody PageResponse page(@ApiParam @RequestBody RolePageRequest pageRequest) {
        logger.info("分页查询角色:{}", pageRequest);

        Page<RoleDTO> page = roleService.page(pageRequest);
        List<RoleDTO> roleDTOList = page.getContent();

        PageInfo pageInfo = page.convertPageInfo();
        List<RoleVO> roleVOList = ModelMapperUtil.get().map(roleDTOList, new TypeToken<List<RoleVO>>() {}.getType());

        logger.info(successQuery);
        return successQueryPageResponse(pageInfo, roleVOList);
    }

}
