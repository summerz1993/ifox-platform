package com.ifox.platform.system.service.impl;

import com.ifox.platform.system.dto.ResourceDTO;
import com.ifox.platform.system.modelmapper.ResourceEOMapDTO;
import com.ifox.platform.system.request.resource.ResourcePageRequest;
import com.ifox.platform.system.service.ResourceService;
import com.ifox.platform.baseservice.impl.GenericServiceImpl;
import com.ifox.platform.common.bean.QueryConditions;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.bean.SimpleOrder;
import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.dao.sys.ResourceDao;
import com.ifox.platform.entity.common.ResourceEO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ResourceServiceImpl extends GenericServiceImpl<ResourceEO, String> implements ResourceService {

    @Autowired
    public void setGenericDao(ResourceDao resourceDao){
        super.genericDao = resourceDao;
    }

    @Override
    public Page<ResourceDTO> page(ResourcePageRequest pageRequest) {
        SimplePage simplePage = pageRequest.convertSimplePage();

        List<QueryProperty> queryProperties = getQueryPropertyList(pageRequest);
        List<SimpleOrder> simpleOrders = pageRequest.getSimpleOrderList();

        QueryConditions queryConditions = new QueryConditions(null, queryProperties, simpleOrders);
        Page<ResourceEO> resourceEOPage = pageByQueryConditions(simplePage, queryConditions);

        return ResourceEOMapDTO.mapPage(resourceEOPage);
    }

    private List<QueryProperty> getQueryPropertyList(ResourcePageRequest pageRequest) {
        List<QueryProperty> queryProperties = new ArrayList<>();

        String name = pageRequest.getName();
        if (!StringUtils.isEmpty(name)) {
            String appendName = "%" + name + "%";
            QueryProperty nameQuery = new QueryProperty("name", EnumDao.Operation.LIKE, appendName);
            queryProperties.add(nameQuery);
        }

        ResourceEO.ResourceEOType type = pageRequest.getType();
        if (type != null) {
            QueryProperty statusQuery = new QueryProperty("type", EnumDao.Operation.EQUAL, type);
            queryProperties.add(statusQuery);
        }

        return queryProperties;
    }
}
