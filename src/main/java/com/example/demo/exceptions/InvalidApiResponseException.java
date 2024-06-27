package com.example.demo.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidApiResponseException extends RuntimeException {

    private final String api;
    private final String reason;

    public InvalidApiResponseException(String api, String reason) {
        super();
        this.api = api;
        this.reason = reason;
    }
}
