package com.example.demo.service;

import com.example.demo.client.OpenWeatherApiRemoteService;
import com.example.demo.data.City;
import com.example.demo.dtos.response.CityForecastDto;
import com.example.demo.dtos.response.CityInfoDto;
import com.example.demo.dtos.response.DailyForecastWithDateDto;
import com.example.demo.dtos.response.DailyForecastWithUnixDto;
import com.example.demo.exceptions.CityAlreadyRegisteredException;
import com.example.demo.exceptions.CityNotFoundException;
import com.example.demo.mapper.CityMapper;
import com.example.demo.repository.CityRepository;
import com.example.demo.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.demo.utils.DateUtils.createMapWithDateAsKey;
import static com.example.demo.utils.DateUtils.getNextDays;
import static com.example.demo.utils.ValidationUtils.validateApiResponseList;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    OpenWeatherApiRemoteService openWeatherApiRemoteService;

    public City registerCity(String cityName) {

        // Validate if city name is already registered
        if (isCityAlreadyRegistered(cityName)) {
            throw new CityAlreadyRegisteredException(cityName);
        }

        // Get city coordinates
        String cityInfoUrl = UrlUtils.createCityInfoUrl(cityName);
        List<CityInfoDto> cityInfoDtoList = openWeatherApiRemoteService.getCityInfo(cityInfoUrl, cityName);

        // Validate API response
        validateApiResponseList(cityInfoDtoList);

        // Extract coordinates
        Map<String, Double> cityCoordinates = getCityCoordinatesMap(cityInfoDtoList);

        // Create Url
        String cityForecastUrl = UrlUtils.createCityForecastUrl(cityCoordinates.get("latitude"), cityCoordinates.get("longitude"));

        City city = City.builder()
                .name(cityName)
                .forecastLink(cityForecastUrl)
                .build();

        return cityRepository.save(city);
    }

    public List<City> getAllCities() {
         return cityRepository.findAll();
    }

    public List<CityForecastDto> getCityForecast(String cityName, int days) {

        // Validate if city is registered
        Optional<City> city = cityRepository.findByName(cityName);

        if (city.isEmpty()) {
            throw new CityNotFoundException();
        }

        // Get city forecast weather
        String cityForecastUrl = city.get().getForecastLink();
        List<DailyForecastWithUnixDto> dailyForecastUnixList = openWeatherApiRemoteService.getCityForecast(cityForecastUrl, cityName).getDaily();

        // Convert Unix timestamps into dates
        List<DailyForecastWithDateDto> dailyForecastDateList = cityMapper.toDailyForecastDateDtos(dailyForecastUnixList);

        // Create map and list of forecast dates in order to obtain desired days
        Map dailyForecastMap  = createMapWithDateAsKey(dailyForecastDateList);
        List<String> forecastDates = getNextDays(days);

        return matchForecastDatesWithForecastMap(dailyForecastMap, forecastDates);
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
    public static List<Object> matchForecastDatesWithForecastMap(Map<String, Object> b, List<String> c) {
        List<Object> matchedObjects = new ArrayList<>();

        for (String date : c) {
            if (b.containsKey(date)) {
                matchedObjects.add(b.get(date));
            }
        }

        return matchedObjects;
    }
}
