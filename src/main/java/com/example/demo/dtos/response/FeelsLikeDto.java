package com.example.demo.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeelsLikeDto {
    private double day;
    private double night;
    private double eve;
    private double morn;

    public FeelsLikeDto() {
    }
}
