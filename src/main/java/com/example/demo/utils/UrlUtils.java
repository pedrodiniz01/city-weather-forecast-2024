package com.example.demo.utils;

import com.example.demo.constants.ApiConstants;

import static com.example.demo.constants.ApiConstants.*;

public class UrlUtils {
    public static String createCityForecastUrl(double latitude, double longitude) {
        return CITY_FORECAST_URL
                + "?" + LATITUDE_PARAM + latitude
                + LONGITUDE_PARAM + longitude
                + API_KEY_PARAM + CITY_FORECAST_API_KEY
                + EXCLUDE_PARAM;
    }

    public static String createCityInfoUrl(String cityName) {
        return ApiConstants.CITY_INFO_URL
                + CITY_NAME_PARAM + cityName
                + API_KEY_PARAM + CITY_INFO_API_KEY;
    }
}
