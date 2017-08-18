package com.ifox.platform.dao.sys.impl;

import com.ifox.platform.dao.common.impl.GenericHibernateDaoImpl;
import com.ifox.platform.dao.sys.ResourceDao;
import com.ifox.platform.entity.common.ResourceEO;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceDaoImpl extends GenericHibernateDaoImpl<ResourceEO, String> implements ResourceDao {

}
