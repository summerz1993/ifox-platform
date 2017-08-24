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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

@Api(tags = "菜单权限管理")
@Controller
@RequestMapping(value = "/menuPermission", headers = {"api-version=1.0", "Authorization"})
public class MenuPermissionController extends BaseController<MenuPermissionVO> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuPermissionService menuPermissionService;

    @ApiOperation("获取菜单")
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public @ResponseBody
    MultiResponse<MenuVO> menu(){
        logger.info("获取树形目录菜单。");
        int maxLevel = menuPermissionService.getMaxLevel();
        logger.info("当前目录最大层级：{}", maxLevel);
        List<MenuPermissionDTO> menuPermissionDTOList = menuPermissionService.list();
        List<MenuVO> menuVOList = new ArrayList<>();

        while (maxLevel != 0){
            int currentLevel = maxLevel;
            List<MenuVO> childMenuVOList = menuVOList.stream()
                .filter(menuVO -> menuVO.getLevel() == currentLevel + 1).collect(Collectors.toList());
            List<MenuPermissionDTO> parentMenuPermissionDTOList = menuPermissionDTOList.stream()
                .filter(menuPermissionDTO -> menuPermissionDTO.getLevel() == currentLevel)
                .collect(Collectors.toList());

            if(childMenuVOList.size() == 0){
                menuVOList.addAll(MenuPermissionDTO.convert(parentMenuPermissionDTOList));
            }else{
                List<MenuVO> parentsMenuVOS = MenuPermissionDTO.convert(parentMenuPermissionDTOList);

                menuVOList.removeAll(childMenuVOList);

                for(MenuVO menuVO : parentsMenuVOS){
                    List<MenuVO> childs = childMenuVOList.stream()
                        .filter(child -> child.getParentId().equals(menuVO.getId()))
                        .collect(Collectors.toList());
                    menuVO.setChildren(childs);
                }

                menuVOList.addAll(parentsMenuVOS);
            }

            maxLevel--;
        }

        logger.info(successQuery);
        return new MultiResponse(SUCCESS, successQuery, menuVOList);
    }

    @ApiOperation("获取菜单详情")
    @RequestMapping(value = "/menu/{id}", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 404, message = "菜单权限不存在")})
    public @ResponseBody
    OneResponse<MenuPermissionVO> menuById(@ApiParam @PathVariable String id){
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

        menuPermissionService.deleteByEntity(menuPermissionEO);
        logger.info(successDelete);

        return successDeleteBaseResponse();
    }

    @ApiOperation("修改菜单权限")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    BaseResponse update(){
        return null;
    }
}
