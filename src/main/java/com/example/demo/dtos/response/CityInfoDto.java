package com.example.demo.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter

public class CityInfoDto {
    private String name;
    private String country;
    private double lat;
    private double lon;

    public CityInfoDto() {
    }
}
