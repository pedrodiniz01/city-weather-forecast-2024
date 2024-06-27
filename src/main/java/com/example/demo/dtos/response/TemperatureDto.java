package com.example.demo.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemperatureDto {
    private double day;
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;

    public TemperatureDto(){
    }
}
