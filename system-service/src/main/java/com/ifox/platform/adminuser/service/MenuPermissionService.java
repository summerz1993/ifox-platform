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
    Integer getMaxLevel();

    /**
     * 查询所有菜单权限
     * @return List<MenuPermissionDTO>
     */
    List<MenuPermissionDTO> listAllDTO();
}
