package com.ifox.platform.adminuser.service.impl;

import com.ifox.platform.adminuser.service.RoleService;
import com.ifox.platform.baseservice.impl.GenericServiceImpl;
import com.ifox.platform.dao.sys.RoleDao;
import com.ifox.platform.entity.sys.RoleEO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends GenericServiceImpl<RoleEO, String> implements RoleService {

    @Autowired
    public void setGenericDao(RoleDao roleDao){
        super.genericDao = roleDao;
    }

}
