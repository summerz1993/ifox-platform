package com.ifox.platform.adminuser.modelmapper;

import com.ifox.platform.adminuser.dto.ResourceDTO;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.entity.common.ResourceEO;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;

public class ResourceEOMapDTO {

    public static Page<ResourceDTO> mapPage(Page<ResourceEO> resourceEOPage) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Converter<ArrayList<ResourceEO>, ArrayList<ResourceDTO>> converter = new AbstractConverter<ArrayList<ResourceEO>, ArrayList<ResourceDTO>>() {
            @Override
            protected ArrayList<ResourceDTO> convert(ArrayList<ResourceEO> source) {
                return modelMapper.map(source, new TypeToken<ArrayList<ResourceDTO>>() {}.getType());
            }
        };
        PropertyMap<Page<ResourceEO>, Page<ResourceDTO>> propertyMap = new PropertyMap<Page<ResourceEO>, Page<ResourceDTO>>() {
            @Override
            protected void configure() {
                using(converter).map(source.getContent(), destination.getContent());
            }
        };
        modelMapper.addMappings(propertyMap);
        modelMapper.createTypeMap(ResourceEO.class, ResourceDTO.class);

        return modelMapper.map(resourceEOPage, new TypeToken<Page<ResourceDTO>>() {}.getType());
    }
}
