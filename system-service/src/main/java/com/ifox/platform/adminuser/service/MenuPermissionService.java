package com.ifox.platform.adminuser.service;

import com.ifox.platform.adminuser.dto.MenuPermissionDTO;
import com.ifox.platform.baseservice.GenericService;
import com.ifox.platform.entity.sys.MenuPermissionEO;

import java.util.List;

public interface MenuPermissionService extends GenericService<MenuPermissionEO, String> {
    /**
     * 获取菜单的最大层级
     * @return 最大层级
     */
    Integer getBottomLevel();

    /**
     * 查询所有菜单权限
     * @return List<MenuPermissionDTO>
     */
    List<MenuPermissionDTO> listAllDTO();

    /**
     * 删除菜单权限和角色的关联关系
     * @param menuId
     */
    void deleteMenuRoleRelation(String menuId);

    /**
     * 查询所有子菜单
     * @param id
     * @return
     */
    List<MenuPermissionEO> listChildMenu(String id);

    /**
     * 删除MenuPermission以及对应的关联数据
     * @param menuPermissionEO
     */
    void delete(MenuPermissionEO menuPermissionEO);
}
