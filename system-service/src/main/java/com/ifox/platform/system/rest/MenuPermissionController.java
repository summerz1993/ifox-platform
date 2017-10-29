package com.ifox.platform.system.rest;

import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.common.rest.response.MultiResponse;
import com.ifox.platform.common.rest.response.OneResponse;
import com.ifox.platform.system.dto.MenuPermissionDTO;
import com.ifox.platform.system.entity.AdminUserEO;
import com.ifox.platform.system.entity.MenuPermissionEO;
import com.ifox.platform.system.entity.ResourceEO;
import com.ifox.platform.system.request.menuPermission.MenuPermissionSaveRequest;
import com.ifox.platform.system.request.menuPermission.MenuPermissionUpdateRequest;
import com.ifox.platform.system.response.MenuPermissionVO;
import com.ifox.platform.system.response.MenuVO;
import com.ifox.platform.system.service.AdminUserService;
import com.ifox.platform.system.service.MenuPermissionService;
import com.ifox.platform.system.service.ResourceService;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.jwt.JWTUtil;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import com.jsoniter.JsonIterator;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ifox.platform.common.constant.RestStatusConstant.*;

@Api(tags = "菜单权限管理")
@Controller
@RequestMapping(value = "/menuPermission", headers = {"api-version=1.0", "Authorization"})
public class MenuPermissionController extends BaseController<MenuPermissionVO> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Autowired
    private MenuPermissionService menuPermissionService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ResourceService resourceService;

    @ApiOperation("获取菜单")
    @RequestMapping(value = "/get/menu", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public @ResponseBody
    MultiResponse<MenuVO> getMenu(){
        String uuid = UUIDUtil.randomUUID();
        logger.info("获取树形目录菜单 uuid:{}", uuid);

        List<MenuPermissionEO> menuPermissionEOList = menuPermissionService.listAll();

//        List<MenuPermissionDTO> allMPDTOList = menuPermissionService.listAllDTO();
        List<MenuVO> menuVOList = new ArrayList<>();
        int topLevel = 1;
        int bottomLevel = menuPermissionService.getBottomLevel();

        for (int i = bottomLevel ; i >= topLevel; i --) {
            int level = i;
            //临时变量
            List<MenuVO> newMenuVOList = new ArrayList<>();
            List<MenuPermissionEO> currentLevelMPEOList = menuPermissionEOList.stream().filter(dto -> dto.getLevel() == level).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(currentLevelMPEOList))
                newMenuVOList.addAll(MenuPermissionDTO.convertEOToVO(currentLevelMPEOList));
            for (MenuVO menuVO : newMenuVOList) {
                //设置children来源于menuVOList
                List<MenuVO> childrenList = menuVOList.stream().filter(vo -> menuVO.getId().equals(vo.getParentId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(childrenList))
                    menuVO.setChildren(childrenList);
            }
            menuVOList.clear();
            menuVOList = newMenuVOList;
        }

        logger.info(successQuery + " uuid:{}", uuid);
        return new MultiResponse(SUCCESS, successQuery, menuVOList);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation("获取菜单详情")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 404, message = "菜单权限不存在")})
    public @ResponseBody
    OneResponse<MenuPermissionVO> get(@ApiParam @PathVariable @NotBlank String id, HttpServletResponse response){
        String uuid = UUIDUtil.randomUUID();
        logger.info("查询菜单权限 id:{}, uuid:{}", id, uuid);
        MenuPermissionEO menuPermissionEO = menuPermissionService.get(id);
        if (menuPermissionEO == null){
            logger.info("此菜单权限不存在 uuid:{}", uuid);
            return super.notFoundOneResponse("此菜单权限不存在", response);
        }

        MenuPermissionVO menuPermissionVO = new MenuPermissionVO();
        ModelMapperUtil.get().map(menuPermissionEO, menuPermissionVO);

        if(!StringUtils.isEmpty(menuPermissionEO.getCreator())){
            AdminUserEO adminUserEO = adminUserService.get(menuPermissionEO.getCreator());
            menuPermissionVO.setCreatorName(adminUserEO.getLoginName());
        }

        if(!StringUtils.isEmpty(menuPermissionEO.getResource())){
            ResourceEO resourceEO = resourceService.get(menuPermissionEO.getResource());
            menuPermissionVO.setResourceName(resourceEO.getName());
        }

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryOneResponse(menuPermissionVO);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation("添加菜单权限")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({
        @ApiResponse(code = 708, message = "父菜单权限不存在")
    })
    public @ResponseBody
    OneResponse<MenuPermissionVO> save(@ApiParam @RequestBody @Validated MenuPermissionSaveRequest saveRequest, @RequestHeader("Authorization") String token, HttpServletResponse response){
        String uuid = UUIDUtil.randomUUID();
        logger.info("保存菜单权限 saveRequest:{}, uuid:{}", saveRequest.toString(), uuid);

        MenuPermissionEO menuPermissionEO = new MenuPermissionEO();
        ModelMapperUtil.get().map(saveRequest, menuPermissionEO);

        String payload = JWTUtil.getPayloadStringByToken(token);
        String userId = JsonIterator.deserialize(payload).get("userId").toString();
        menuPermissionEO.setCreator(userId);

        MenuPermissionEO parentMenu = menuPermissionService.get(menuPermissionEO.getParentId());
        if(parentMenu == null){
            logger.info("父菜单权限不存在 uuid:{}", uuid);
            return new OneResponse(PARENT_MENU_PERMISSION_NOT_FOUND, "父菜单权限不存在", response);
        }

        menuPermissionEO.setLevel(parentMenu.getLevel() + 1);
        menuPermissionService.save(menuPermissionEO);

        MenuPermissionVO menuPermissionVO = new MenuPermissionVO();
        ModelMapperUtil.get().map(menuPermissionEO, menuPermissionVO);

        logger.info(successSave + " uuid:{}", uuid);
        return new OneResponse(SUCCESS, successSave, menuPermissionVO);
    }

    @ApiOperation("删除菜单权限")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiResponses({
        @ApiResponse(code = 404, message = "菜单权限不存在"),
        @ApiResponse(code = 707, message = "系统内置菜单不可删除"),
        @ApiResponse(code = 706, message = "菜单包含子菜单，请先删除子菜单")
    })
    public @ResponseBody
    BaseResponse delete(@ApiParam @PathVariable @NotBlank String id, HttpServletResponse response){
        String uuid = UUIDUtil.randomUUID();
        logger.info("删除菜单权限 id:{}, uuid:{}", id, uuid);
        MenuPermissionEO menuPermissionEO = menuPermissionService.get(id);

        if (menuPermissionEO == null){
            logger.info("此菜单权限不存在 uuid:{}", uuid);
            return super.notFoundOneResponse("此菜单权限不存在", response);
        }

        if(menuPermissionEO.getBuildinSystem()){
            logger.info("为系统内置菜单，不可删除 id:{}, uuid:{}", id, uuid);
            response.setStatus(BUILD_IN_SYSTEM_CAN_NOT_DELETE);
            return new BaseResponse(BUILD_IN_SYSTEM_CAN_NOT_DELETE, "系统内置菜单不可删除！");
        }

        List<MenuPermissionEO> menuPermissionEOList = menuPermissionService.listChildMenu(id);
        if(menuPermissionEOList != null && menuPermissionEOList.size() > 0){
            logger.info("当前菜单包含子菜单 uuid:{}", uuid);
            response.setStatus(CONTAIN_CHILD_MENU_CAN_NOT_DELETE);
            return new BaseResponse(CONTAIN_CHILD_MENU_CAN_NOT_DELETE, "菜单包含子菜单，请先删除子菜单！");
        }

        menuPermissionService.delete(menuPermissionEO);

        logger.info(successDelete + " uuid:{}", uuid);
        return successDeleteBaseResponse();
    }

    @SuppressWarnings("unchecked")
    @ApiOperation("修改菜单权限")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public @ResponseBody
    OneResponse<MenuPermissionVO> update(@ApiParam @RequestBody @Validated MenuPermissionUpdateRequest updateRequest){
        String uuid = UUIDUtil.randomUUID();
        logger.info("修改菜单权限 updateRequest:{}, uuid:{}", updateRequest.toString(), uuid);

        MenuPermissionEO menuPermissionEO = menuPermissionService.update(updateRequest);
        MenuPermissionVO menuPermissionVO = ModelMapperUtil.get().map(menuPermissionEO, MenuPermissionVO.class);

        logger.info(successUpdate + " uuid:{}", uuid);
        return new OneResponse(SUCCESS, successSave, menuPermissionVO);
    }
}
