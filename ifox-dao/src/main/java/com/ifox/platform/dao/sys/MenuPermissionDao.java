package com.ifox.platform.dao.sys;

import com.ifox.platform.dao.common.GenericDao;
import com.ifox.platform.entity.sys.MenuPermissionEO;

public interface MenuPermissionDao extends GenericDao<MenuPermissionEO, String> {

    /**
     * 获取菜单的最大层级
     * @return 最大层级
     */
    Integer getBottomLevel();
}
