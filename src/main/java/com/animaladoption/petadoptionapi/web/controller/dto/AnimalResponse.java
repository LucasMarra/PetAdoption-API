package com.animaladoption.petadoptionapi.web.controller.dto;

import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.domain.AnimalStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AnimalResponse {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private AnimalCategory category;
    private LocalDateTime createdAt;
    private AnimalStatus status;
}


