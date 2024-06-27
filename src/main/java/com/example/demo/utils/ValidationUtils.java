package com.example.demo.utils;

import com.example.demo.exceptions.InvalidApiResponseException;

import java.util.List;
import java.util.Objects;

public class ValidationUtils {

    public static void validateApiResponseList(List list) {
        if (Objects.isNull(list) || list.size() == 0) {
            throw new InvalidApiResponseException("Open Weather API", "Empty response.", null);
        }
    }
}
