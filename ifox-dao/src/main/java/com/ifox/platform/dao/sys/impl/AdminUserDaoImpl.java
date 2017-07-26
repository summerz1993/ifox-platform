package com.ifox.platform.dao.sys.impl;

import com.ifox.platform.dao.sys.AdminUserDao;
import com.ifox.platform.dao.common.impl.GenericHibernateDaoImpl;
import com.ifox.platform.entity.sys.AdminUserEO;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserDaoImpl extends GenericHibernateDaoImpl<AdminUserEO, String> implements AdminUserDao{
}
