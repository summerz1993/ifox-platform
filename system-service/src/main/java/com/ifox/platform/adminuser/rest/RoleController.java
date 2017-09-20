package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.dto.RoleDTO;
import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.request.role.RolePageRequest;
import com.ifox.platform.adminuser.request.role.RoleQueryRequest;
import com.ifox.platform.adminuser.request.role.RoleSaveRequest;
import com.ifox.platform.adminuser.request.role.RoleUpdateRequest;
import com.ifox.platform.adminuser.response.RoleVO;
import com.ifox.platform.adminuser.service.RoleService;
import com.ifox.platform.common.exception.BuildinSystemException;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.PageInfo;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.common.rest.response.MultiResponse;
import com.ifox.platform.common.rest.response.OneResponse;
import com.ifox.platform.common.rest.response.PageResponse;
import com.ifox.platform.entity.sys.MenuPermissionEO;
import com.ifox.platform.entity.sys.RoleEO;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import io.swagger.annotations.*;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
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
    @ApiResponses({ @ApiResponse(code = 709, message = "角色标识符已经存在") })
    public @ResponseBody BaseResponse save(@ApiParam @RequestBody RoleSaveRequest saveRequest, HttpServletResponse response){
        String uuid = UUIDUtil.randomUUID();
        logger.info("保存角色信息 saveRequest:{}, uuid:{}", saveRequest.toString(), uuid);

        String identifier = saveRequest.getIdentifier();
        RoleDTO byIdentifier = roleService.getByIdentifier(identifier);
        if (byIdentifier != null) {
            logger.info("角色标识符已经存在 uuid:{}", uuid);
            return existedIdentifierBaseResponse("角色标识符已经存在", response);
        }

        RoleEO roleEO = ModelMapperUtil.get().map(saveRequest, RoleEO.class);
        roleEO.setMenuPermissionEOList(saveRequest.getMenuPermissionEOList());

        roleService.save(roleEO);

        logger.info(successSave + " uuid:{}", uuid);
        return successSaveBaseResponse();
    }

    @ApiOperation("删除角色信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiResponses({ @ApiResponse(code = 404, message = "角色不存在"),
        @ApiResponse(code = 400, message = "无效请求：ids为空")})
    public @ResponseBody BaseResponse delete(@ApiParam @RequestBody String[] ids, HttpServletResponse response) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("删除角色信息 ids:{}, uuid:{}", Arrays.toString(ids), uuid);

        if (ids.length == 0){
            logger.info("无效请求,ids为空 uuid:{}", uuid);
            return invalidRequestBaseResponse(response);
        }

        try {
            roleService.delete(ids);
        } catch (NotFoundAdminUserException e) {
            logger.info("删除的角色不存在 uuid:{}", uuid);
            return notFoundBaseResponse("删除的角色不存在", response);
        } catch (BuildinSystemException e) {
            logger.info("系统内置角色不允许删除 uuid:{}", uuid);
            return deleteBuildinSystemErrorBaseResponse("系统内置角色不允许删除", response);
        }

        logger.info(successDelete + " uuid:{}", uuid);
        return successDeleteBaseResponse();
    }

    @ApiOperation("更新角色信息")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiResponses({ @ApiResponse(code = 404, message = "角色不存在"),
        @ApiResponse(code = 709, message = "角色标识符已经存在")})
    public @ResponseBody BaseResponse update(@ApiParam @RequestBody RoleUpdateRequest updateRequest, HttpServletResponse response) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("更新用户信息 updateRequest:{}, uuid:{}", updateRequest, uuid);

        String id = updateRequest.getId();
        RoleEO roleEO = roleService.get(id);
        if (roleEO == null) {
            logger.info("角色不存在 id:{}, uuid:{}", id, uuid);
            return super.notFoundBaseResponse("角色不存在", response);
        }

        String identifier = updateRequest.getIdentifier();
        if (!roleEO.getIdentifier().equals(identifier)) {
            RoleDTO byIdentifier = roleService.getByIdentifier(identifier);
            if (byIdentifier != null && !roleEO.getIdentifier().equals(identifier)) {
                logger.info("角色标识符已经存在 uuid:{}", uuid);
                return existedIdentifierBaseResponse("角色标识符已经存在", response);
            }
        }

        ModelMapperUtil.get().map(updateRequest, roleEO);
        roleEO.setMenuPermissionEOList(updateRequest.getMenuPermissionEOList());

        roleService.update(roleEO);

        logger.info(successUpdate + " uuid:{}", uuid);
        return successUpdateBaseResponse();
    }

    @ApiOperation("查询单个角色信息")
    @RequestMapping(value = "/get/{roleId}", method = RequestMethod.GET)
    @ApiResponses({ @ApiResponse(code = 404, message = "角色不存在") })
    public @ResponseBody OneResponse get(@ApiParam @PathVariable(name = "roleId") String roleId, HttpServletResponse response) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("查询单个角色信息 roleId:{}, uuid:{}", roleId, uuid);

        RoleEO roleEO = roleService.get(roleId);
        if (roleEO == null) {
            logger.info("角色不存在 roleId:{}, uuid:{}", roleId, uuid);
            return super.notFoundOneResponse("角色不存在", response);
        }
        List<MenuPermissionEO> menuPermissionEOList = roleEO.getMenuPermissionEOList();
        List<String> menuPermissions = new ArrayList<>();
        for (MenuPermissionEO menu:menuPermissionEOList) {
            menuPermissions.add(menu.getId());
        }
        RoleVO vo = ModelMapperUtil.get().map(roleEO, RoleVO.class);
        vo.setMenuPermissions(menuPermissions);

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryOneResponse(vo);
    }

    @ApiOperation("分页查询角色")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public @ResponseBody PageResponse page(@ApiParam @RequestBody RolePageRequest pageRequest) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("分页查询角色 pageRequest:{}, uuid:{}", pageRequest, uuid);

        Page<RoleDTO> page = roleService.page(pageRequest);
        List<RoleDTO> roleDTOList = page.getContent();

        PageInfo pageInfo = page.convertPageInfo();
        List<RoleVO> roleVOList = ModelMapperUtil.get().map(roleDTOList, new TypeToken<List<RoleVO>>() {}.getType());

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryPageResponse(pageInfo, roleVOList);
    }

    @ApiOperation("list查询角色")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public @ResponseBody
    MultiResponse list(@ApiParam @RequestBody RoleQueryRequest queryRequest) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("分页查询角色 queryRequest:{}, uuid:{}", queryRequest, uuid);

        List<RoleDTO> roleDTOList = roleService.list(queryRequest);
        List<RoleVO> roleVOList = ModelMapperUtil.get().map(roleDTOList, new TypeToken<List<RoleVO>>() {}.getType());

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryMultiResponse(roleVOList);
    }

}
