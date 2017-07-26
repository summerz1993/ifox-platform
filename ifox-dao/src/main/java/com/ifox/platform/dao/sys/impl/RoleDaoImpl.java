package com.ifox.platform.dao.sys.impl;

import com.ifox.platform.dao.common.impl.GenericHibernateDaoImpl;
import com.ifox.platform.dao.sys.RoleDao;
import com.ifox.platform.entity.sys.RoleEO;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends GenericHibernateDaoImpl<RoleEO, String> implements RoleDao {
}
