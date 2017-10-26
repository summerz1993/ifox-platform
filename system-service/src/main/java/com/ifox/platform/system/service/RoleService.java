package com.ifox.platform.system.service;

import com.ifox.platform.system.dto.RoleDTO;
import com.ifox.platform.system.entity.RoleEO;
import com.ifox.platform.system.exception.NotFoundAdminUserException;
import com.ifox.platform.system.request.role.RolePageRequest;
import com.ifox.platform.system.request.role.RoleQueryRequest;
import com.ifox.platform.common.exception.BuildinSystemException;
import com.ifox.platform.common.page.Page;

import java.util.List;

public interface RoleService {

    /**
     * 分页查询角色
     * @param pageRequest 分页参数
     * @return Page<RoleDTO>
     */
    Page<RoleDTO> page(RolePageRequest pageRequest);

    /**
     * 删除多个角色
     * @param ids ID
     */
    void delete(String[] ids) throws NotFoundAdminUserException, BuildinSystemException;

    /**
     * 通过identifier查询角色
     * @param identifier identifier
     * @return RoleDTO
     */
    RoleDTO getByIdentifier(String identifier);

    /**
     * list查询
     * @param queryRequest RoleQueryRequest
     * @return List<RoleDTO>
     */
    List<RoleDTO> list(RoleQueryRequest queryRequest);

}
