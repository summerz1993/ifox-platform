package com.ifox.platform.system.service;

import com.ifox.platform.common.exception.BuildinSystemException;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.system.entity.RoleEO;
import com.ifox.platform.system.exception.NotFoundRoleException;
import com.ifox.platform.system.request.role.RolePageRequest;
import com.ifox.platform.system.request.role.RoleQueryRequest;
import com.ifox.platform.system.request.role.RoleUpdateRequest;

import java.util.List;

public interface RoleService {

    /**
     * 分页查询角色
     * @param pageRequest 分页参数
     * @return SimplePage<RoleEO>
     */
    SimplePage<RoleEO> page(RolePageRequest pageRequest);

    /**
     * 删除多个角色
     * @param ids ID
     */
    void deleteMulti(String[] ids) throws NotFoundRoleException, BuildinSystemException;

    /**
     * 通过identifier查询角色
     * @param identifier identifier
     * @return RoleEO
     */
    RoleEO getByIdentifier(String identifier);

    /**
     * list查询
     * @param queryRequest RoleQueryRequest
     * @return List<RoleEO>
     */
    List<RoleEO> list(RoleQueryRequest queryRequest);

    /**
     * 保存角色
     * @param roleEO 角色实体
     */
    void save(RoleEO roleEO);

    /**
     * 通过ID查询角色
     * @param id 角色ID
     * @return RoleEO
     */
    RoleEO get(String id);

    /**
     * 更新角色
     * @param updateRequest 角色信息
     */
    void update(RoleUpdateRequest updateRequest);

    /**
     * 根据角色ID和权限ID统计数量 - 判定此角色是否有对应的权限
     * @param roleIdList roleIdList
     * @param menuPermissionId menuPermissionId
     * @return Integer
     */
    Integer countByRoleIdListAndMenuPermission(String[] roleIdList, String menuPermissionId);

}
