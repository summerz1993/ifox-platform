package com.ifox.platform.system.modelmapper;

import com.ifox.platform.system.dto.RoleDTO;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.system.entity.RoleEO;
import com.ifox.platform.utility.modelmapper.EOMapDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

public class RoleEOMapDTO {

    public static Page<RoleDTO> mapPage(Page<RoleEO> roleEOPage) {
        EOMapDTO eoMapDTO = new EOMapDTO<RoleEO, RoleDTO>();
        ModelMapper modelMapper = eoMapDTO.initModelMapper();
        modelMapper.createTypeMap(RoleEO.class, RoleDTO.class);

        return modelMapper.map(roleEOPage, new TypeToken<Page<RoleDTO>>() {}.getType());
    }

}
