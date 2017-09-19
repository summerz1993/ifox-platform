package com.ifox.platform.adminuser.service.impl;

import com.ifox.platform.adminuser.dto.MenuPermissionDTO;
import com.ifox.platform.adminuser.service.MenuPermissionService;
import com.ifox.platform.baseservice.impl.GenericServiceImpl;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.dao.sys.MenuPermissionDao;
import com.ifox.platform.entity.sys.MenuPermissionEO;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    /**
     * 删除菜单权限和角色的关联关系
     * @param menuId
     */
    @Override
    @Transactional
    public void deleteMenuRoleRelation(String menuId) {
        menuPermissionDao.deleteMenuRoleRelation(menuId);
    }

    /**
     * 查询所有子菜单
     * @param id
     * @return
     */
    @Override
    public List<MenuPermissionEO> listChildMenu(String id) {
        List<QueryProperty> queryProperties = new ArrayList<>();
        queryProperties.add(new QueryProperty("parentId", EnumDao.Operation.EQUAL, id));
        return listByQueryProperty(queryProperties);
    }

    /**
     * 删除MenuPermission以及对应的关联数据
     * @param menuPermissionEO
     */
    @Override
    @Transactional
    public void delete(MenuPermissionEO menuPermissionEO) {
        menuPermissionDao.deleteMenuRoleRelation(menuPermissionEO.getId());
        super.deleteByEntity(menuPermissionEO);
    }
}
