package com.example.demo.controller;

import com.example.demo.data.City;
import com.example.demo.dtos.RegisterCityDto;
import com.example.demo.mapper.CityMapper;
import com.example.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {
    @Autowired
    private CityService cityService;

    @Autowired
    private CityMapper cityMapper;

    @PostMapping("/register")
    public ResponseEntity<?> registerCity(@RequestBody RegisterCityDto dto) {
        cityService.registerCity(dto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("City Created.");
    }
}
