package com.ifox.platform.adminuser.modelmapper;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.entity.sys.AdminUserEO;
import com.ifox.platform.utility.modelmapper.EOMapDTO;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;

public class AdminUserEOMapDTO {

    public static Page<AdminUserDTO> mapPage(Page<AdminUserEO> adminUserEOPage) {
        EOMapDTO eoMapDTO = new EOMapDTO<AdminUserEO, AdminUserDTO>();
        ModelMapper modelMapper = eoMapDTO.initModelMapper();
        modelMapper.createTypeMap(AdminUserEO.class, AdminUserDTO.class);

        return modelMapper.map(adminUserEOPage, new TypeToken<Page<AdminUserDTO>>() {}.getType());
    }

}
