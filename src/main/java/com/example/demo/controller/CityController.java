package com.example.demo.controller;

import com.example.demo.client.OpenWeatherApiRemoteService;
import com.example.demo.dtos.request.RegisterCityDto;
import com.example.demo.dtos.response.CityInfoDto;
import com.example.demo.exceptions.InvalidApiResponseException;
import com.example.demo.mapper.CityMapper;
import com.example.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {
    @Autowired
    private CityService cityService;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    OpenWeatherApiRemoteService openWeatherApiRemoteService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCity(@RequestBody RegisterCityDto dto) {
        try {
            cityService.registerCity(dto.getName());
        }
        catch (InvalidApiResponseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("City Created.");
    }
}
