package com.animaladoption.petadoptionapi.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends RuntimeException {
    private final HttpStatus httpStatus;

    private final String message;


    public ApiException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        httpStatus = HttpStatus.BAD_REQUEST;
        message = exceptionMessage.getMessage();
    }
}
