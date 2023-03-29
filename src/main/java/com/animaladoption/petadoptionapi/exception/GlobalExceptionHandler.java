package com.animaladoption.petadoptionapi.exception;

import com.animaladoption.petadoptionapi.web.controller.dto.ApiExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> handleApiException(ApiException e) {
        // Handle the ApiException by returning a ResponseEntity with the error message and the appropriate HttpStatus
        var body = new ApiExceptionResponse(e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(body);
    }
}
