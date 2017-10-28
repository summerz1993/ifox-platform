package com.ifox.platform.system.service;

import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.system.entity.ResourceEO;
import com.ifox.platform.system.exception.NotFoundResourceException;
import com.ifox.platform.system.request.resource.ResourcePageRequest;
import com.ifox.platform.system.request.resource.ResourceUpdateRequest;

import java.util.List;

public interface ResourceService {

    SimplePage<ResourceEO> page(ResourcePageRequest pageRequest);

    void save(ResourceEO resourceEO);

    void deleteMulti(String[] ids) throws NotFoundResourceException;

    ResourceEO get(String id);

    void update(ResourceUpdateRequest resourceUpdateRequest);

    List<ResourceEO> listAll();

    ResourceEO getByController(String controller);
}
