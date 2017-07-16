package com.ifox.platform.adminuser.service.impl;

import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.baseservice.impl.GenericServiceImpl;
import com.ifox.platform.dao.adminuser.AdminUserDao;
import com.ifox.platform.entity.adminuser.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yezhang on 7/14/2017.
 */
@Service
public class AdminUserServiceImpl extends GenericServiceImpl<AdminUser, String> implements AdminUserService{

    @Autowired
    public void setGenericDao(AdminUserDao adminUserDao){
        super.genericDao = adminUserDao;
    }

}
