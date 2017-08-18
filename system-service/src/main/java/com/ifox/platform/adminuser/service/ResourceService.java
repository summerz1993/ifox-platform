package com.ifox.platform.adminuser.service;

import com.ifox.platform.adminuser.dto.ResourceDTO;
import com.ifox.platform.adminuser.request.resource.ResourcePageRequest;
import com.ifox.platform.baseservice.GenericService;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.entity.common.ResourceEO;

public interface ResourceService extends GenericService<ResourceEO, String > {

    Page<ResourceDTO> page(ResourcePageRequest pageRequest);
}
