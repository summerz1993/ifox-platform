package com.ifox.platform.adminuser.service;

import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.exception.RepeatedAdminUserException;
import com.ifox.platform.baseservice.GenericService;
import com.ifox.platform.entity.adminuser.AdminUserEO;

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

}
