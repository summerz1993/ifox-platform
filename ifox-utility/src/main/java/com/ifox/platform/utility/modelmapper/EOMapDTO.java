package com.ifox.platform.utility.modelmapper;

import com.ifox.platform.common.page.SimplePage;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;

public class EOMapDTO<E, D> {

    public ModelMapper initModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Converter<ArrayList<E>, ArrayList<D>> converter = new AbstractConverter<ArrayList<E>, ArrayList<D>>() {
            @Override
            protected ArrayList<D> convert(ArrayList<E> source) {
                return modelMapper.map(source, new TypeToken<ArrayList<D>>() {}.getType());
            }
        };
        PropertyMap<SimplePage<E>, SimplePage<D>> propertyMap = new PropertyMap<SimplePage<E>, SimplePage<D>>() {
            @Override
            protected void configure() {
                using(converter).map(source.getContent(), destination.getContent());
            }
        };
        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }

}
