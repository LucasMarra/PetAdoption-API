package com.animaladoption.petadoptionapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Animal {
    private String id;
    private String externalId;
    private String name;
    private String description;
    private String imageUrl;
    private AnimalCategory category;
    private AnimaStatus status;
}
