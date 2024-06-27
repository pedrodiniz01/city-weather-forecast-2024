package com.example.demo.mapper;

import com.example.demo.data.City;
import com.example.demo.dtos.RegisterCityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CityMapper {
    @Mapping(target = "id", ignore = true)
    City toCity(RegisterCityDto dto);
}
