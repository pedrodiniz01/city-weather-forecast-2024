package com.example.demo.constants;

public class ApiConstants {

    // City Info API constants
    public static final String CITY_INFO_URL = "http://api.openweathermap.org/geo/1.0/direct";
    public static final String CITY_INFO_API_KEY = "b8a62f6cd8354cf606bce6e9f2987e82";

    // City Forecast API constants
    public static final String CITY_FORECAST_URL = "https://api.openweathermap.org/data/3.0/onecall";
    public static final String CITY_FORECAST_API_KEY = "d450c64ee02b2fd15f6b1b9c628b7660";

    // Query parameters
    public static final String LATITUDE_PARAM = "lat=";
    public static final String LONGITUDE_PARAM = "&lon=";
    public static final String API_KEY_PARAM = "&appid=";
    public static final String TIMESTAMP_PARAM = "&date=";
    public static final String EXCLUDE_PARAM = "&exclude=minutely,hourly,current,alerts";
    public static final String CITY_NAME_PARAM = "?q=";
}
