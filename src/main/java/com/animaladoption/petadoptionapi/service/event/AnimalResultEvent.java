package com.animaladoption.petadoptionapi.service.event;

import lombok.Data;

@Data
public class AnimalResultEvent {
    private String imageUrl;
    private String name;
    private String description;
    private String externalId;
}
