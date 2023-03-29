package com.animaladoption.petadoptionapi.web.controller.dto;

import com.animaladoption.petadoptionapi.exception.ApiException;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ApiExceptionResponse {
    int statusCode;
    String message;

    public ApiExceptionResponse(HttpStatus statusCode, String message) {
        this.statusCode = statusCode.value();
        this.message = message;
    }

    public ApiExceptionResponse(ApiException apiException) {
        statusCode = apiException.getHttpStatus().value();
        message = apiException.getMessage();
    }
}
