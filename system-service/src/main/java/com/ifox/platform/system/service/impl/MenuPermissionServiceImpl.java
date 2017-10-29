package com.ifox.platform.system.service.impl;

import com.ifox.platform.system.dao.MenuPermissionRepository;
import com.ifox.platform.system.entity.MenuPermissionEO;
import com.ifox.platform.system.request.menuPermission.MenuPermissionUpdateRequest;
import com.ifox.platform.system.service.MenuPermissionService;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MenuPermissionServiceImpl implements MenuPermissionService {

    @Resource
    private MenuPermissionRepository menuPermissionRepository;

    /**
     * 获取菜单的最大层级
     * @return 最大层级
     */
    @Override
    public Integer getBottomLevel() {
        return menuPermissionRepository.getBottomLevel();
    }

    /**
     * 查询所有菜单权限
     * @return List<MenuPermissionEO>
     */
    @Override
    public List<MenuPermissionEO> listAll() {
        return menuPermissionRepository.findAll();
    }

    /**
     * 删除菜单权限和角色的关联关系
     * @param menuId menuId
     */
    @Override
    @Transactional
    public void deleteMenuRoleRelation(String menuId) {
        menuPermissionRepository.deleteMenuRoleRelation(menuId);
    }

    /**
     * 查询所有子菜单
     * @param parentId
     * @return List<MenuPermissionEO>
     */
    @Override
    public List<MenuPermissionEO> listChildMenu(String parentId) {
        return menuPermissionRepository.findAllByParentIdEquals(parentId);
    }

    /**
     * 删除MenuPermission以及对应的关联数据
     * @param menuPermissionEO
     */
    @Override
    @Transactional
    public void delete(MenuPermissionEO menuPermissionEO) {
        deleteMenuRoleRelation(menuPermissionEO.getId());
        menuPermissionRepository.delete(menuPermissionEO);
    }

    @Override
    public MenuPermissionEO get(String id) {
        return menuPermissionRepository.findOne(id);
    }

    @Override
    @Transactional
    public void save(MenuPermissionEO menuPermissionEO) {
        menuPermissionRepository.save(menuPermissionEO);
    }

    @Override
    public MenuPermissionEO update(MenuPermissionUpdateRequest updateRequest) {
        MenuPermissionEO menuPermissionEO = menuPermissionRepository.findOne(updateRequest.getId());
        ModelMapperUtil.get().map(updateRequest, menuPermissionEO);
        return menuPermissionEO;
    }

    @Override
    public MenuPermissionEO getByURL(String url) {
        return menuPermissionRepository.findFirstByUrlEquals(url);
    }
}
