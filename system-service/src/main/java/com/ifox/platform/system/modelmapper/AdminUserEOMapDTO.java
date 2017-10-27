package com.ifox.platform.system.modelmapper;

import com.ifox.platform.system.dto.AdminUserDTO;
import com.ifox.platform.system.entity.AdminUserEO;
import com.ifox.platform.utility.modelmapper.EOMapDTO;
import org.modelmapper.*;

public class AdminUserEOMapDTO {

    public static Page<AdminUserDTO> mapPage(Page<AdminUserEO> adminUserEOPage) {
        EOMapDTO eoMapDTO = new EOMapDTO<AdminUserEO, AdminUserDTO>();
        ModelMapper modelMapper = eoMapDTO.initModelMapper();
        modelMapper.createTypeMap(AdminUserEO.class, AdminUserDTO.class);

        return modelMapper.map(adminUserEOPage, new TypeToken<Page<AdminUserDTO>>() {}.getType());
    }

}
