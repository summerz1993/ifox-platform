package com.ifox.platform.system.service;

import com.ifox.platform.system.entity.MenuPermissionEO;
import com.ifox.platform.system.request.menuPermission.MenuPermissionUpdateRequest;

import java.util.List;

public interface MenuPermissionService {
    /**
     * 获取菜单的最大层级
     * @return 最大层级
     */
    Integer getBottomLevel();

    /**
     * 查询所有菜单权限
     * @return List<MenuPermissionEO>
     */
    List<MenuPermissionEO> listAll();

    /**
     * 删除菜单权限和角色的关联关系
     * @param menuId menuId
     */
    void deleteMenuRoleRelation(String menuId);

    /**
     * 查询所有子菜单
     * @param parentId parentId
     * @return List<MenuPermissionEO>
     */
    List<MenuPermissionEO> listChildMenu(String parentId);

    /**
     * 删除MenuPermission以及对应的关联数据
     * @param menuPermissionEO MenuPermissionEO
     */
    void delete(MenuPermissionEO menuPermissionEO);

    MenuPermissionEO get(String id);

    void save(MenuPermissionEO menuPermissionEO);

    MenuPermissionEO update(MenuPermissionUpdateRequest updateRequest);

    MenuPermissionEO getByURL(String url);

}
