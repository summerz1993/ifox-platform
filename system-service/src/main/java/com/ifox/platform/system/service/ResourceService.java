package com.ifox.platform.system.service;

import com.ifox.platform.common.page.Page;
import com.ifox.platform.system.dto.ResourceDTO;
import com.ifox.platform.system.request.resource.ResourcePageRequest;

public interface ResourceService {

    Page<ResourceDTO> page(ResourcePageRequest pageRequest);
}
