package com.animaladoption.petadoptionapi.web.controller.validation;

import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.domain.AnimalStatus;
import com.animaladoption.petadoptionapi.exception.ApiException;
import com.animaladoption.petadoptionapi.exception.ExceptionMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class AnimalControllerRequestValidation {
    public void validateGetAnimalsRequest(Map<String, String> getAnimalRequestParam) {
        Optional.ofNullable(getAnimalRequestParam.get("category"))
                .map(categoryName -> {
                    try {
                        return AnimalCategory.valueOf(categoryName);
                    } catch (IllegalArgumentException e) {
                        throw new ApiException(ExceptionMessage.INVALID_CATEGORY);
                    }
                });

        Optional.ofNullable(getAnimalRequestParam.get("status"))
                .map(categoryName -> {
                    try {
                        return AnimalStatus.valueOf(categoryName);
                    } catch (IllegalArgumentException e) {
                        throw new ApiException(ExceptionMessage.INVALID_STATUS);
                    }
                });

        Optional.ofNullable(getAnimalRequestParam.get("createdAt"))
                .map(updatedAt -> {
                    try {
                        return LocalDateTime.parse(updatedAt);
                    } catch (Exception e) {
                        throw new ApiException(ExceptionMessage.INVALID_DATE_TIME_FORMAT);
                    }
                });

    }
}
