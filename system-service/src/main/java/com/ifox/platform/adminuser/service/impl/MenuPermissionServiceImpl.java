package com.ifox.platform.adminuser.service.impl;

import com.ifox.platform.adminuser.dto.MenuPermissionDTO;
import com.ifox.platform.adminuser.service.MenuPermissionService;
import com.ifox.platform.baseservice.impl.GenericServiceImpl;
import com.ifox.platform.dao.sys.MenuPermissionDao;
import com.ifox.platform.entity.sys.MenuPermissionEO;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuPermissionServiceImpl extends GenericServiceImpl<MenuPermissionEO, String> implements MenuPermissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuPermissionDao menuPermissionDao;

    @Autowired
    public void setGenericDao(MenuPermissionDao menuPermissionDao){
        super.genericDao = menuPermissionDao;
    }

    @Override
    public int getMaxLevel() {
        return menuPermissionDao.getMaxLevel();
    }

    @Override
    public List<MenuPermissionDTO> list() {
        List<MenuPermissionEO> menuPermissionEOS = listAll();
        return ModelMapperUtil.get().map(menuPermissionEOS, new TypeToken<List<MenuPermissionDTO>>() {}.getType());
    }
}
