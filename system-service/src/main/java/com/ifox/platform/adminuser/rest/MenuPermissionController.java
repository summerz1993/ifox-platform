package com.ifox.platform.adminuser.rest;

import com.ifox.platform.adminuser.dto.MenuPermissionDTO;
import com.ifox.platform.adminuser.request.menuPermission.MenuPermissionRequest;
import com.ifox.platform.adminuser.response.MenuPermissionVO;
import com.ifox.platform.adminuser.response.MenuVO;
import com.ifox.platform.adminuser.service.MenuPermissionService;
import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.common.rest.response.MultiResponse;
import com.ifox.platform.common.rest.response.OneResponse;
import com.ifox.platform.entity.sys.MenuPermissionEO;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ifox.platform.common.constant.RestStatusConstant.CONTAIN_CHILD_MENU;
import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

@Api(tags = "菜单权限管理")
@Controller
@RequestMapping(value = "/menuPermission", headers = {"api-version=1.0", "Authorization"})
public class MenuPermissionController extends BaseController<MenuPermissionVO> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuPermissionService menuPermissionService;

    @ApiOperation("获取菜单")
    @RequestMapping(value = "/get/menu", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public @ResponseBody
    MultiResponse<MenuVO> getMenu(){
        logger.info("获取树形目录菜单");

        List<MenuPermissionDTO> allMPDTOList = menuPermissionService.listAllDTO();
        List<MenuVO> menuVOList = new ArrayList<>();
        int topLevel = 1;
        int bottomLevel = menuPermissionService.getBottomLevel();

        for (int i = bottomLevel ; i >= topLevel; i --) {
            int level = i;
            //临时变量
            List<MenuVO> newMenuVOList = new ArrayList<>();
            List<MenuPermissionDTO> currentLevelMPDTOList = allMPDTOList.stream().filter(dto -> dto.getLevel() == level).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(currentLevelMPDTOList))
                newMenuVOList.addAll(MenuPermissionDTO.convertToVO(currentLevelMPDTOList));
            for (MenuVO menuVO : newMenuVOList) {
                //设置children来源于menuVOList
                List<MenuVO> childrenList = menuVOList.stream().filter(vo -> menuVO.getId().equals(vo.getParentId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(childrenList))
                    menuVO.setChildren(childrenList);
            }
            menuVOList.clear();
            menuVOList = newMenuVOList;
        }

        logger.info(successQuery);
        return new MultiResponse(SUCCESS, successQuery, menuVOList);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation("获取菜单详情")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 404, message = "菜单权限不存在")})
    public @ResponseBody
    OneResponse<MenuPermissionVO> get(@ApiParam @PathVariable String id){
        logger.info("查询菜单权限：{}", id);
        MenuPermissionEO menuPermissionEO = menuPermissionService.get(id);
        if (menuPermissionEO == null){
            logger.info("此菜单权限不存在");
            return super.notFoundOneResponse("此菜单权限不存在");
        }

        MenuPermissionVO menuPermissionVO = new MenuPermissionVO();
        ModelMapperUtil.get().map(menuPermissionEO, menuPermissionVO);

        logger.info(successQuery);
        return successQueryOneResponse(menuPermissionVO);
    }

    @ApiOperation("添加菜单权限")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    BaseResponse save(@ApiParam @RequestBody MenuPermissionRequest request){
        logger.info("保存菜单权限：{}", request.toString());

        MenuPermissionEO menuPermissionEO = new MenuPermissionEO();
        ModelMapperUtil.get().map(request, menuPermissionEO);

        menuPermissionService.save(menuPermissionEO);

        logger.info(successSave);
        return successSaveBaseResponse();
    }

    @ApiOperation("删除菜单权限")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiResponses({@ApiResponse(code = 404, message = "菜单权限不存在")})
    public @ResponseBody
    BaseResponse delete(@ApiParam @PathVariable String id){
        logger.info("删除菜单权限：{}", id);
        MenuPermissionEO menuPermissionEO = menuPermissionService.get(id);

        if (menuPermissionEO == null){
            logger.info("此菜单权限不存在");
            return super.notFoundOneResponse("此菜单权限不存在");
        }

        List<MenuPermissionEO> menuPermissionEOList = menuPermissionService.listChildMenu(id);
        if(menuPermissionEOList != null && menuPermissionEOList.size() > 0){
            logger.info("当前菜单包含子菜单");
            return new BaseResponse(CONTAIN_CHILD_MENU, "菜单包含子菜单，请先删除子菜单！");
        }

        menuPermissionService.deleteMenuRoleRelation(id);
        menuPermissionService.deleteByEntity(menuPermissionEO);
        logger.info(successDelete);

        return successDeleteBaseResponse();
    }

    @ApiOperation("修改菜单权限")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public @ResponseBody
    BaseResponse update(){
        return null;
    }
}
