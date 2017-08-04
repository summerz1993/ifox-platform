package com.ifox.platform.adminuser.modelmapper;

import com.ifox.platform.adminuser.dto.RoleDTO;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.entity.sys.RoleEO;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;

public class RoleEOMapDTO {

    public static Page<RoleDTO> mapPage(Page<RoleEO> roleEOPage) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Converter<ArrayList<RoleEO>, ArrayList<RoleDTO>> converter = new AbstractConverter<ArrayList<RoleEO>, ArrayList<RoleDTO>>() {
            @Override
            protected ArrayList<RoleDTO> convert(ArrayList<RoleEO> source) {
                return modelMapper.map(source, new TypeToken<ArrayList<RoleDTO>>() {}.getType());
            }
        };
        PropertyMap<Page<RoleEO>, Page<RoleDTO>> propertyMap = new PropertyMap<Page<RoleEO>, Page<RoleDTO>>() {
            @Override
            protected void configure() {
                using(converter).map(source.getContent(), destination.getContent());
            }
        };
        modelMapper.addMappings(propertyMap);
        modelMapper.createTypeMap(RoleEO.class, RoleDTO.class);

        return modelMapper.map(roleEOPage, new TypeToken<Page<RoleDTO>>() {}.getType());
    }

}
