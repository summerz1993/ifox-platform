package com.ifox.platform.adminuser.service;

import com.ifox.platform.adminuser.dto.RoleDTO;
import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.request.role.RolePageRequest;
import com.ifox.platform.adminuser.request.role.RoleQueryRequest;
import com.ifox.platform.baseservice.GenericService;
import com.ifox.platform.common.exception.BuildinSystemException;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.entity.sys.RoleEO;

import java.util.List;

public interface RoleService extends GenericService<RoleEO, String> {

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
