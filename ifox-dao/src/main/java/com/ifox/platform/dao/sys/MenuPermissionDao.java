package com.ifox.platform.dao.sys;

import com.ifox.platform.dao.common.GenericDao;
import com.ifox.platform.entity.sys.MenuPermissionEO;

public interface MenuPermissionDao extends GenericDao<MenuPermissionEO, String> {

    /**
     * 获取菜单的最大层级
     * @return 最大层级
     */
    Integer getBottomLevel();

    /**
     * 删除菜单权限和角色的关联关系
     * @param menuId menuId
     */
    void deleteMenuRoleRelation(String menuId);

    /**
     * 通过URL获取菜单权限实体
     * @param URL URL
     * @return 菜单权限实体
     */
    MenuPermissionEO getByURL(String URL);
}
