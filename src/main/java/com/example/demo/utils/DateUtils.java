package com.example.demo.utils;

import com.example.demo.dtos.response.DailyForecastWithDateDto;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtils {

    public static String convertUnixToDateString(long unixTimestamp) {
        Date date = new Date(unixTimestamp * 1000L);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(date);
    }

    public static List<String> getNextDays(int days) {
        List<String> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < days; i++) {
            dates.add(currentDate.plusDays(i).format(formatter));
        }

        return dates;
    }

    public static Map<String, DailyForecastWithDateDto> createMapWithDateAsKey(List<DailyForecastWithDateDto> forecasts) {
        Map<String, DailyForecastWithDateDto> forecastMap = new HashMap<>();

        for (DailyForecastWithDateDto forecast : forecasts) {
            forecastMap.put(forecast.getDt(), forecast);
        }
        return forecastMap;
    }
}
