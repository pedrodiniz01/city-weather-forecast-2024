package com.example.demo.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CityForecastDto {
    private double lat;
    private double lon;
    private String timezone;
    private int timezone_offset;
    private List<DailyForecastUnixDto> daily;

    public CityForecastDto() {
    }
}
