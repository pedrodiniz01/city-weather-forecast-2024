package com.example.demo.client;

import com.example.demo.dtos.response.CityInfoDto;
import com.example.demo.exceptions.InvalidApiResponseException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.List;

@Service
public class OpenWeatherApiRemoteService {

    private final String cityInfoUrl = "http://api.openweathermap.org/geo/1.0/direct";
    private final String cityInfoApiKey = "b8a62f6cd8354cf606bce6e9f2987e82";
    private final String cityForecastUrl = "https://api.openweathermap.org/data/3.0/onecall";
    private final String cityForecastApiKey = "d450c64ee02b2fd15f6b1b9c628b7660";
    private final WebClient webClient;

    public OpenWeatherApiRemoteService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public List<CityInfoDto> getCityInfo(String cityName) {
        String url = cityInfoUrl
                + "?q=" + cityName
                + "&appid=" + cityInfoApiKey;

        try {
            List<CityInfoDto> cityInfoList = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(CityInfoDto.class)
                    .collectList()
                    .block();
            return cityInfoList;
        } catch (RuntimeException e) {
            throw new InvalidApiResponseException("Open Weather API - City Info", e.getMessage());
        }
    }

    public List<CityInfoDto> getCityForecast(double latitude, double longitude) {
        long currentUnixTimestamp = Instant.now().getEpochSecond();

        String url = cityForecastUrl
                + "?lat=" + latitude
                + "&lon=" + longitude
                + "&appid=" + cityForecastApiKey
                + "&date=" + currentUnixTimestamp
                + "&exclude=minutely,hourly,current";

        try {
            List<CityInfoDto> cityInfoList = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(CityInfoDto.class)
                    .collectList()
                    .block();
            return cityInfoList;
        } catch (RuntimeException e) {
            throw new InvalidApiResponseException("Open Weather API - One Call", e.getMessage());
        }
    }
}
