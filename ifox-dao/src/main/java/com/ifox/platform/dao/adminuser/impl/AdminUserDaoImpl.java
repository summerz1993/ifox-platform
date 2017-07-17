package com.ifox.platform.dao.adminuser.impl;

import com.ifox.platform.dao.adminuser.AdminUserDao;
import com.ifox.platform.dao.common.impl.GenericHibernateDaoImpl;
import com.ifox.platform.entity.adminuser.AdminUserEO;
import org.springframework.stereotype.Repository;

/**
 * Created by yezhang on 7/14/2017.
 */
@Repository
public class AdminUserDaoImpl extends GenericHibernateDaoImpl<AdminUserEO, String> implements AdminUserDao{
}