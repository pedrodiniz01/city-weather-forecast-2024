package com.example.demo.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityAlreadyRegisteredException extends RuntimeException {
    public CityAlreadyRegisteredException(String message) {
        super(message);
    }
}
