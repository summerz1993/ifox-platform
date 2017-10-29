package com.ifox.platform.system.service;

import com.ifox.platform.common.exception.BuildinSystemException;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.system.entity.AdminUserEO;
import com.ifox.platform.system.exception.NotFoundAdminUserException;
import com.ifox.platform.system.exception.RepeatedAdminUserException;
import com.ifox.platform.system.request.adminuser.AdminUserPageRequest;
import com.ifox.platform.system.request.adminuser.AdminUserQueryRequest;
import com.ifox.platform.system.request.adminuser.AdminUserUpdateRequest;
import com.ifox.platform.utility.jwt.JWTPayload;

import java.util.List;

public interface AdminUserService{

    /**
     * 验证用户名和密码是否正确
     * @param loginName 用户名
     * @param password 密码
     * @return true:存在 false:不存在
     */
    Boolean validLoginNameAndPassword(String loginName, String password) throws NotFoundAdminUserException, RepeatedAdminUserException;

    /**
     * 根据登录名查询用户
     * @param loginName 登录名
     * @return 用户信息
     */
    AdminUserEO getByLoginName(String loginName);

    /**
     * 根据登录名生成PayLoad
     * @param loginName 登录名
     * @return JWTPayload
     */
    JWTPayload generatePayload(String loginName);

    /**
     * 分页查询
     * @param pageRequest 分页查询条件
     * @return Page<AdminUserEO>
     */
    SimplePage<AdminUserEO> page(AdminUserPageRequest pageRequest);

    /**
     * 列表查询
     * @param queryRequest 查询条件
     * @return List<AdminUserDTO>
     */
    List<AdminUserEO> list(AdminUserQueryRequest queryRequest);

    /**
     * 删除多个用户
     * @param ids ID
     */
    void delete(String[] ids) throws NotFoundAdminUserException, BuildinSystemException;

    AdminUserEO get(String id);

    void save(AdminUserEO adminUserEO);

    AdminUserEO update(AdminUserUpdateRequest updateRequest);

    void updatePassword(String password, String id);

}
