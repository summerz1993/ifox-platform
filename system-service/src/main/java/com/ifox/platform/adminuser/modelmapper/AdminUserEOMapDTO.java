package com.ifox.platform.adminuser.modelmapper;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.entity.sys.AdminUserEO;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;

public class AdminUserEOMapDTO {

    public static Page<AdminUserDTO> mapPage(Page<AdminUserEO> adminUserEOPage) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Converter<ArrayList<AdminUserEO>, ArrayList<AdminUserDTO>> converter = new AbstractConverter<ArrayList<AdminUserEO>, ArrayList<AdminUserDTO>>() {
            @Override
            protected ArrayList<AdminUserDTO> convert(ArrayList<AdminUserEO> source) {
                return modelMapper.map(source, new TypeToken<ArrayList<AdminUserDTO>>() {}.getType());
            }
        };
        PropertyMap<Page<AdminUserEO>, Page<AdminUserDTO>> propertyMap = new PropertyMap<Page<AdminUserEO>, Page<AdminUserDTO>>() {
            @Override
            protected void configure() {
                using(converter).map(source.getContent(), destination.getContent());
            }
        };
        modelMapper.addMappings(propertyMap);
        modelMapper.createTypeMap(AdminUserEO.class, AdminUserDTO.class);

        return modelMapper.map(adminUserEOPage, new TypeToken<Page<AdminUserDTO>>() {}.getType());
    }

}
