package com.will.taskmanager.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<org.hibernate.collection.spi.PersistentBag, List<?>> persistentBagToListConverter =
                context -> (List<?>) context.getSource().stream().collect(Collectors.toList());

        modelMapper.addConverter(persistentBagToListConverter);
        return modelMapper;
    }
}
