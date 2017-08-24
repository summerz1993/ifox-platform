package com.ifox.platform.dao.sys;

import com.ifox.platform.dao.common.GenericDao;
import com.ifox.platform.entity.sys.MenuPermissionEO;

public interface MenuPermissionDao extends GenericDao<MenuPermissionEO, String> {
    /**
     * 获取最大菜单层级
     * @return
     */
    int getMaxLevel();
}
