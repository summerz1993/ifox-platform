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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuPermissionServiceImpl extends GenericServiceImpl<MenuPermissionEO, String> implements MenuPermissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuPermissionDao menuPermissionDao;

    @Autowired
    public void setGenericDao(MenuPermissionDao menuPermissionDao){
        super.genericDao = menuPermissionDao;
    }

    /**
     * 获取菜单的最大层级
     * @return 最大层级
     */
    @Override
    public Integer getBottomLevel() {
        return menuPermissionDao.getBottomLevel();
    }

    /**
     * 查询所有菜单权限
     * @return List<MenuPermissionDTO>
     */
    @Override
    public List<MenuPermissionDTO> listAllDTO() {
        List<MenuPermissionEO> menuPermissionEOS = listAll();
        return ModelMapperUtil.get().map(menuPermissionEOS, new TypeToken<List<MenuPermissionDTO>>() {}.getType());
    }
}
