package com.example.demo.service;

import com.example.demo.data.City;
import com.example.demo.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public City registerCity(String cityName) {
        City city = new City();
        city.setName(cityName);
       return cityRepository.save(city);
    }

}
