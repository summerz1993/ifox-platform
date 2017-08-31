package com.ifox.platform.dao.sys.impl;

import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.dao.common.impl.GenericHibernateDaoImpl;
import com.ifox.platform.dao.sys.ResourceDao;
import com.ifox.platform.entity.common.ResourceEO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ResourceDaoImpl extends GenericHibernateDaoImpl<ResourceEO, String> implements ResourceDao {

    /**
     * 通过controller URL获取资源
     * @param controller 控制器
     * @return 资源实体
     */
    @Override
    public ResourceEO getByController(String controller) {
        QueryProperty queryProperty = new QueryProperty("controller", EnumDao.Operation.EQUAL, controller);
        List<QueryProperty> queryPropertyList = new ArrayList<>();
        queryPropertyList.add(queryProperty);
        List<ResourceEO> resourceEOList = listByQueryProperty(queryPropertyList);
        if (resourceEOList != null && resourceEOList.size() > 0) {
            return resourceEOList.get(0);
        }
        return null;
    }
}
