package com.example.demo.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidApiResponseException extends RuntimeException {

    private final String api;
    private final String reason;
    private final String additionalInfo;


    public InvalidApiResponseException(String api, String reason, String additionalInfo) {
        super();
        this.api = api;
        this.reason = reason;
        this.additionalInfo = additionalInfo;
    }
}
