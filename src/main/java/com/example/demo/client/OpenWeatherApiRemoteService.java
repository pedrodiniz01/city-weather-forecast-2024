package com.example.demo.client;

import com.example.demo.dtos.response.CityForecastDto;
import com.example.demo.dtos.response.CityInfoDto;
import com.example.demo.exceptions.InvalidApiResponseException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OpenWeatherApiRemoteService {
    private final WebClient webClient;

    public OpenWeatherApiRemoteService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public List<CityInfoDto> getCityInfo(String url, String cityName) {

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

    public CityForecastDto getCityForecast(String url, String cityName) {

        try {
            CityForecastDto cityForecastDto = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(CityForecastDto.class)
                    .block();
            return cityForecastDto;
        } catch (RuntimeException e) {
            throw new InvalidApiResponseException("Open Weather API - Forecast Info", e.getMessage(), cityName);
        }
    }
}
