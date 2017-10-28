package com.ifox.platform.system.modelmapper;

import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.system.dto.ResourceDTO;
import com.ifox.platform.system.entity.ResourceEO;
import com.ifox.platform.utility.modelmapper.EOMapDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

public class ResourceEOMapDTO {

    public static SimplePage<ResourceDTO> mapPage(SimplePage<ResourceEO> resourceEOPage) {
        EOMapDTO eoMapDTO = new EOMapDTO<ResourceEO, ResourceDTO>();
        ModelMapper modelMapper = eoMapDTO.initModelMapper();
        modelMapper.createTypeMap(ResourceEO.class, ResourceDTO.class);

        return modelMapper.map(resourceEOPage, new TypeToken<SimplePage<ResourceDTO>>() {}.getType());
    }
}
