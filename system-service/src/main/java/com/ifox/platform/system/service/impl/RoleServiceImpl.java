package com.ifox.platform.system.service.impl;

import com.ifox.platform.common.exception.BuildinSystemException;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.jpa.converter.PageRequestConverter;
import com.ifox.platform.jpa.converter.SpringDataPageConverter;
import com.ifox.platform.system.dao.RoleRepository;
import com.ifox.platform.system.entity.MenuPermissionEO;
import com.ifox.platform.system.entity.RoleEO;
import com.ifox.platform.system.exception.NotFoundRoleException;
import com.ifox.platform.system.request.role.RolePageRequest;
import com.ifox.platform.system.request.role.RoleQueryRequest;
import com.ifox.platform.system.request.role.RoleUpdateRequest;
import com.ifox.platform.system.service.MenuPermissionService;
import com.ifox.platform.system.service.RoleService;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.ifox.platform.common.constant.ExceptionStatusConstant.BUILDIN_SYSTEM_EXP;
import static com.ifox.platform.common.constant.ExceptionStatusConstant.NOT_FOUND_ROLE_EXP;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private MenuPermissionService menuPermissionService;

    /**
     * 分页查询角色
     * @param pageRequest 分页参数
     * @return Page<RoleDTO>
     */
    @Override
    public SimplePage<RoleEO> page(RolePageRequest pageRequest) {
        Pageable pageable = PageRequestConverter.convertToSpringDataPageable(pageRequest);
        Page<RoleEO> page = roleRepository.findAllByNameLikeAndStatusEquals(pageRequest.getName(), pageRequest.getStatus(), pageable);
        return new SpringDataPageConverter<RoleEO>().convertToSimplePage(page);
    }

    /**
     * 删除多个角色
     * @param ids ID
     */
    @Override
    @Transactional
    @Modifying
    public void deleteMulti(String[] ids) throws NotFoundRoleException, BuildinSystemException {
        for (String id : ids) {
            RoleEO roleEO = roleRepository.findOne(id);
            if (roleEO == null) {
                throw new NotFoundRoleException(NOT_FOUND_ROLE_EXP, "角色不存在");
            } else if(roleEO.getBuildinSystem()) {
                throw new BuildinSystemException(BUILDIN_SYSTEM_EXP, "系统内置角色，不允许删除");
            } else {
                roleRepository.delete(roleEO);
            }
        }
    }

    /**
     * 通过identifier查询角色
     * @param identifier identifier
     * @return RoleDTO
     */
    @Override
    public RoleEO getByIdentifier(String identifier) {
        List<RoleEO> roleEOList = roleRepository.findByIdentifier(identifier);
        if (!CollectionUtils.isEmpty(roleEOList)) {
            return roleEOList.get(0);
        }
        return null;
    }

    /**
     * list查询
     * @param queryRequest RoleQueryRequest
     * @return List<RoleDTO>
     */
    @Override
    public List<RoleEO> list(RoleQueryRequest queryRequest) {
        return roleRepository.findByNameLikeAndStatusEquals(queryRequest.getName(), queryRequest.getStatus());
    }

    /**
     * 保存角色
     * @param roleEO 角色实体
     */
    @Override
    @Transactional
    @Modifying
    public void save(RoleEO roleEO) {
        roleRepository.save(roleEO);
    }

    /**
     * 通过ID查询角色
     * @param id 角色ID
     * @return RoleEO
     */
    @Override
    public RoleEO get(String id) {
        return roleRepository.getOne(id);
    }

    /**
     * 更新角色
     * @param updateRequest 角色信息
     */
    @Override
    @Transactional
    @Modifying
    public void update(RoleUpdateRequest updateRequest) {
        RoleEO roleEO = roleRepository.getOne(updateRequest.getId());
        ModelMapperUtil.get().map(updateRequest, roleEO);

        List<String> menuPermissionIdList = updateRequest.getMenuPermissions();
        List<MenuPermissionEO> menuPermissionEOList = new ArrayList<>();
        for (String menuPermissionId : menuPermissionIdList) {
            MenuPermissionEO menuPermissionEO = menuPermissionService.get(menuPermissionId);
            menuPermissionEOList.add(menuPermissionEO);
        }
        roleEO.setMenuPermissionEOList(menuPermissionEOList);
    }

    /**
     * 根据角色ID和权限ID统计数量 - 判定此角色是否有对应的权限
     * @param roleIdList roleIdList
     * @param menuPermissionId menuPermissionId
     * @return Integer
     */
    @Override
    public Integer countByRoleIdListAndMenuPermission(String[] roleIdList, String menuPermissionId) {
        return roleRepository.countByRoleIdListAndMenuPermission(roleIdList, menuPermissionId);
    }

}
