package com.ifox.platform.dao.sys;

import com.ifox.platform.dao.common.GenericDao;
import com.ifox.platform.entity.sys.RoleEO;

public interface RoleDao extends GenericDao<RoleEO, String> {

    /**
     * 根据角色ID和菜单权限ID计算对应数量
     * @param roleIdList
     * @param menuPermissionId
     * @return 数量
     */
    Integer countByRoleIdListAndMenuPermission(String[] roleIdList, String menuPermissionId);

}
