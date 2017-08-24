package com.ifox.platform.adminuser.service;

import com.ifox.platform.adminuser.dto.MenuPermissionDTO;
import com.ifox.platform.baseservice.GenericService;
import com.ifox.platform.entity.sys.MenuPermissionEO;

import java.util.List;

public interface MenuPermissionService extends GenericService<MenuPermissionEO, String> {
    /**
     * 获取最大菜单层级
     * @return
     */
    int getMaxLevel();

    List<MenuPermissionDTO> list();
}
