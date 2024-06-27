package com.example.demo.configuration.mapper;

import com.example.demo.mapper.CityMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfig {
    @Bean
    public CityMapper cityMapper() {
        return Mappers.getMapper(CityMapper.class);
    }
}
