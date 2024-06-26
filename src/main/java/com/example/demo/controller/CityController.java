package com.example.demo.controller;

import com.example.demo.data.City;
import com.example.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CityController {
    @Autowired
    private CityService cityService;
    @PostMapping("/cities")
    public City registerCity(@RequestParam String name) {
        return cityService.registerCity(name);
    }
}
