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
import static com.example.demo.utils.DateUtils.getDesiredDays;
import static com.example.demo.utils.ValidationUtils.validateApiResponseList;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private OpenWeatherApiRemoteService openWeatherApiRemoteService;

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

        // Extract relevant data
        Map<String, Double> cityCoordinates = extractCityCoordinates(cityInfoDtoList);
        String country = cityInfoDtoList.get(0).getCountry();

        // Create Url for city forecast
        String cityForecastUrl = UrlUtils.createCityForecastUrl(cityCoordinates.get("latitude"), cityCoordinates.get("longitude"));

        City city = City.builder()
                .name(cityName)
                .country(country)
                .forecastLink(cityForecastUrl)
                .build();

        return cityRepository.save(city);
    }

    public List<City> getAllCities() {
         return cityRepository.findAll();
    }

    public List<CityForecastDto> getCityForecast(String cityName, int days) {
        // Validate if city is registered
        City city = cityRepository.findByName(cityName).orElseThrow(CityNotFoundException::new);

        // Get city forecast weather
        String cityForecastUrl = city.getForecastLink();
        List<DailyForecastWithUnixDto> dailyForecastUnixList = openWeatherApiRemoteService.getCityForecast(cityForecastUrl, cityName).getDaily();

        // Convert Unix timestamps into dates
        List<DailyForecastWithDateDto> dailyForecastDateList = cityMapper.toDailyForecastDateDtos(dailyForecastUnixList);

        // Create map with date as key for easy lookup
        Map dailyForecastMap = createMapWithDateAsKey(dailyForecastDateList);

        // Get list of desired days as strings
        List<String> desiredDays = getDesiredDays(days);

        return matchDesiredDaysWithForecastMap(dailyForecastMap, desiredDays);
    }

    private Map<String, Double> extractCityCoordinates(List<CityInfoDto> cities) {
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
    public static List<Object> matchDesiredDaysWithForecastMap(Map<String, Object> forecastMap, List<String> desiredDays) {
        List<Object> filteredList = new ArrayList<>();

        for (String date : desiredDays) {
            if (forecastMap.containsKey(date)) {
                filteredList.add(forecastMap.get(date));
            }
        }
        return filteredList;
    }
}
