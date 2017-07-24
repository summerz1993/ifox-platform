package com.ifox.platform.adminuser.service;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.exception.RepeatedAdminUserException;
import com.ifox.platform.baseservice.GenericService;
import com.ifox.platform.entity.adminuser.AdminUserEO;
import com.ifox.platform.utility.jwt.JWTPayload;

/**
 * Created by yezhang on 7/14/2017.
 */
public interface AdminUserService extends GenericService<AdminUserEO, String>{

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
    AdminUserDTO getByLoginName(String loginName);

    /**
     * 根据登录名生成PayLoad
     * @param loginName 登录名
     * @return JWTPayload
     */
    JWTPayload generatePayload(String loginName);

}
