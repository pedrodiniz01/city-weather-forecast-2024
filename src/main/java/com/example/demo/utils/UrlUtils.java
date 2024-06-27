package com.example.demo.utils;

import static com.example.demo.constants.ApiConstants.*;

public class UrlUtils {
    public static String createCityForecastUrl(double latitude, double longitude, long currentUnixTimestamp) {
        return CITY_FORECAST_URL
                + "?" + LATITUDE_PARAM + latitude
                + LONGITUDE_PARAM + longitude
                + API_KEY_PARAM + CITY_FORECAST_API_KEY
                + TIMESTAMP_PARAM + currentUnixTimestamp
                + EXCLUDE_PARAM;
    }
}
