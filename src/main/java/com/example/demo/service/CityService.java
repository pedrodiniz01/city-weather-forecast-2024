package com.example.demo.service;

import com.example.demo.client.OpenWeatherApiRemoteService;
import com.example.demo.data.City;
import com.example.demo.dtos.response.CityForecastDto;
import com.example.demo.dtos.response.CityInfoDto;
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

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    OpenWeatherApiRemoteService openWeatherApiRemoteService;

    public City registerCity(String cityName) {

        // Get city coordinates
        List<CityInfoDto> cityInfoDtoList = openWeatherApiRemoteService.getCityInfo(cityName);

        // Validate Get City response
        validateGetCityResponse(cityInfoDtoList);

        // Extract coordinates
        Map<String, Double> cityCoordinates = getCityCoordinatesMap(cityInfoDtoList);

        // Create Url
        long currentUnixTimestamp = Instant.now().getEpochSecond();
        String url = UrlUtils.createCityForecastUrl(cityCoordinates.get("latitude"), cityCoordinates.get("longitude"), currentUnixTimestamp);

        City city = new City();
        city.setName(cityName);
        city.setForecastLink(url);

        return cityRepository.save(city);
    }

    private void validateGetCityResponse(List<CityInfoDto> cityInfoDtoList) {
        if (Objects.isNull(cityInfoDtoList) || cityInfoDtoList.size() == 0) {
            throw new InvalidApiResponseException("Open Weather API", "Empty response.", null);
        }
    }

    private void validateGetCityForecastResponse(List<CityInfoDto> cityInfoDtoList) {
        if (Objects.isNull(cityInfoDtoList) || cityInfoDtoList.size() == 0) {
            throw new InvalidApiResponseException("Open Weather API", "Empty response.", null);
        }
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

}
