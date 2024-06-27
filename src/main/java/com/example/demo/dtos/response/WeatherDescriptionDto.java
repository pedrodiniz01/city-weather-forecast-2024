package com.example.demo.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDescriptionDto {
    private int id;
    private String main;
    private String description;
    private String icon;

    public WeatherDescriptionDto() {
    }
}
