package com.example.demo.client;

import com.example.demo.constants.ApiConstants;
import com.example.demo.dtos.response.CityForecastDto;
import com.example.demo.dtos.response.CityInfoDto;
import com.example.demo.exceptions.InvalidApiResponseException;
import com.example.demo.utils.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.List;

import static com.example.demo.constants.ApiConstants.*;

@Service
public class OpenWeatherApiRemoteService {
    private final WebClient webClient;

    public OpenWeatherApiRemoteService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public List<CityInfoDto> getCityInfo(String cityName) {
        String url = ApiConstants.CITY_INFO_URL
                + CITY_NAME_PARAM + cityName
                + API_KEY_PARAM + CITY_INFO_API_KEY;

        try {
            List<CityInfoDto> cityInfoList = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(CityInfoDto.class)
                    .collectList()
                    .block();
            return cityInfoList;
        } catch (RuntimeException e) {
            throw new InvalidApiResponseException("Open Weather - City Info", e.getMessage(), cityName);
        }
    }

    public CityForecastDto getCityForecast(double latitude, double longitude, String cityName) {
        long currentUnixTimestamp = Instant.now().getEpochSecond();

        String url = UrlUtils.createCityForecastUrl(latitude, longitude, currentUnixTimestamp);

        try {
            CityForecastDto cityForecastDto = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(CityForecastDto.class)
                    .block();
            return cityForecastDto;
        } catch (RuntimeException e) {
            throw new InvalidApiResponseException("Open Weather API - Forecast", e.getMessage(), cityName);
        }
    }
}
