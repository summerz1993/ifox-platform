package com.ifox.platform.adminuser.service.impl;

import com.ifox.platform.adminuser.dto.RoleDTO;
import com.ifox.platform.adminuser.modelmapper.RoleEOMapDTO;
import com.ifox.platform.adminuser.request.role.RolePageRequest;
import com.ifox.platform.adminuser.request.role.RoleQueryRequest;
import com.ifox.platform.adminuser.service.RoleService;
import com.ifox.platform.baseservice.impl.GenericServiceImpl;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.dao.sys.RoleDao;
import com.ifox.platform.entity.sys.RoleEO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends GenericServiceImpl<RoleEO, String> implements RoleService {

    @Autowired
    public void setGenericDao(RoleDao roleDao){
        super.genericDao = roleDao;
    }

    /**
     * 分页查询角色
     * @param pageRequest 分页参数
     * @return Page<RoleDTO>
     */
    @Override
    public Page<RoleDTO> page(RolePageRequest pageRequest) {
        SimplePage simplePage = pageRequest.convertSimplePage();

        List<QueryProperty> queryPropertyList = getQueryPropertyList(pageRequest);

        Page<RoleEO> roleEOPage = pageByQueryProperty(simplePage, queryPropertyList);

        return RoleEOMapDTO.mapPage(roleEOPage);
    }

    /**
     * 根据page请求生成查询参数
     * @param pageRequest 分页请求
     * @return List<QueryProperty>
     */
    private List<QueryProperty> getQueryPropertyList(RolePageRequest pageRequest) {
        List<QueryProperty> queryPropertyList = new ArrayList<>();

        String name = pageRequest.getName();
        if (!StringUtils.isEmpty(name)) {
            QueryProperty nameQuery = new QueryProperty("name", EnumDao.Operation.EQUAL, name);
            queryPropertyList.add(nameQuery);
        }

        RoleEO.RoleEOStatus status = pageRequest.getStatus();
        if (status != null) {
            QueryProperty statusQuery = new QueryProperty("status", EnumDao.Operation.EQUAL, status);
            queryPropertyList.add(statusQuery);
        }
        return queryPropertyList;
    }

}
