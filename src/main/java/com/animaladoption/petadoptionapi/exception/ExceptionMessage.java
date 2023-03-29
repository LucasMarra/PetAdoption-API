package com.animaladoption.petadoptionapi.exception;

import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.domain.AnimalStatus;
import lombok.Getter;

import java.util.Arrays;

public enum ExceptionMessage {

    INTERNAL_ERROR("Ops... Something went wrong. Try again later."),
    INVALID_CATEGORY("Invalid category. Please choose a valid category from the following list: " + Arrays.toString(AnimalCategory.values())),
    INVALID_STATUS("Invalid status. Please choose a valid status from the following list: " + Arrays.toString(AnimalStatus.values())),
    INVALID_DATE_TIME_FORMAT("Invalid datetime format. Please use the ISO-8601 datetime format, such as yyyy-MM-ddTHH:mm:ss."),
    ;

    @Getter
    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}
