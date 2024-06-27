package com.example.demo.controller;

import com.example.demo.client.OpenWeatherApiRemoteService;
import com.example.demo.dtos.request.RegisterCityDto;
import com.example.demo.exceptions.CityAlreadyRegisteredException;
import com.example.demo.exceptions.InvalidApiResponseException;
import com.example.demo.mapper.CityMapper;
import com.example.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        } catch (CityAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.OK).body(String.format("City '%s' has already been registered.", dto.getName()));
        } catch (InvalidApiResponseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Unknown city name '%s'.", dto.getName()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("City '%s' has been registered with success.", dto.getName()));
    }
}
