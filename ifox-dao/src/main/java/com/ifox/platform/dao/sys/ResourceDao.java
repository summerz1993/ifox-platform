package com.ifox.platform.dao.sys;

import com.ifox.platform.dao.common.GenericDao;
import com.ifox.platform.entity.common.ResourceEO;

public interface ResourceDao extends GenericDao<ResourceEO, String> {

    /**
     * 通过controller URL获取资源
     * @param controller 控制器
     * @return 资源实体
     */
    ResourceEO getByController(String controller);

}
