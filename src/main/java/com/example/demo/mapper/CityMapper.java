package com.example.demo.mapper;

import com.example.demo.data.City;
import com.example.demo.dtos.request.RegisterCityDto;
import com.example.demo.dtos.response.DailyForecastDateDto;
import com.example.demo.dtos.response.DailyForecastUnixDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static com.example.demo.utils.DateUtils.convertUnixToDateString;

@Mapper
public interface CityMapper {
    @Mapping(target = "id", ignore = true)
    City toCity(RegisterCityDto dto);

    @Mapping(target = "dt", ignore = true)
    @Mapping(target = "sunrise", ignore = true)
    @Mapping(target = "sunset", ignore = true)
    @Mapping(target = "moonrise", ignore = true)
    @Mapping(target = "moonset", ignore = true)
    DailyForecastDateDto toDailyForecastDateDto(DailyForecastUnixDto source);

    @AfterMapping
    default void mapUnixTimestampsToDates(@MappingTarget DailyForecastDateDto dto, DailyForecastUnixDto source) {
        dto.setDt(convertUnixToDateString(source.getDt()));
        dto.setSunrise(convertUnixToDateString(source.getSunrise()));
        dto.setSunset(convertUnixToDateString(source.getSunset()));
        dto.setMoonrise(convertUnixToDateString(source.getMoonrise()));
        dto.setMoonset(convertUnixToDateString(source.getMoonset()));
    }
    List<DailyForecastDateDto> toDailyForecastDateDtos(List<DailyForecastUnixDto> sources);
}
