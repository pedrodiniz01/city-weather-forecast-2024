package com.example.demo.service;

import com.example.demo.client.OpenWeatherApiRemoteService;
import com.example.demo.data.City;
import com.example.demo.dtos.response.CityInfoDto;
import com.example.demo.exceptions.CityAlreadyRegisteredException;
import com.example.demo.exceptions.InvalidApiResponseException;
import com.example.demo.repository.CityRepository;
import com.example.demo.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.demo.utils.ValidationUtils.validateApiResponseList;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    OpenWeatherApiRemoteService openWeatherApiRemoteService;

    public City registerCity(String cityName) {

        // Validate if city name is already registered
        if (isCityAlreadyRegistered(cityName)) {
            throw new CityAlreadyRegisteredException(cityName);
        }

        // Get city coordinates
        List<CityInfoDto> cityInfoDtoList = openWeatherApiRemoteService.getCityInfo(cityName);

        // Validate API response
        validateApiResponseList(cityInfoDtoList);

        // Extract coordinates
        Map<String, Double> cityCoordinates = getCityCoordinatesMap(cityInfoDtoList);

        // Create Url
        String url = UrlUtils.createCityForecastUrl(cityCoordinates.get("latitude"), cityCoordinates.get("longitude"), Instant.now().getEpochSecond());

        City city = City.builder()
                .name(cityName)
                .forecastLink(url)
                .build();

        return cityRepository.save(city);
    }

    private Map<String, Double> getCityCoordinatesMap(List<CityInfoDto> cities) {
        Map<String, Double> cityCoordinatesMap = new HashMap<>();

        if (!cities.isEmpty()) {
            CityInfoDto firstCity = cities.get(0);
            cityCoordinatesMap.put("latitude", firstCity.getLat());
            cityCoordinatesMap.put("longitude", firstCity.getLon());
        }

        return cityCoordinatesMap;
    }

    private boolean isCityAlreadyRegistered(String name) {
        return cityRepository.existsByName(name);
    }
}
