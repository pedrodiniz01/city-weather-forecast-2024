package com.example.demo.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DailyForecastDateDto {
    private String dt;
    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;
    private double moon_phase;
    private String summary;
    private TemperatureDto temp;
    private FeelsLikeDto feels_like;
    private int pressure;
    private int humidity;
    private double dew_point;
    private double wind_speed;
    private int wind_deg;
    private double wind_gust;
    private List<WeatherDescriptionDto> weather;
    private int clouds;
    private double pop;
    private double rain;
    private double uvi;

    public DailyForecastDateDto() {

    }
}
