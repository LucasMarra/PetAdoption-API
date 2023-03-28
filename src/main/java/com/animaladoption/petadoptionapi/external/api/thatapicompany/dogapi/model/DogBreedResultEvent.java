package com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogBreedResultEvent {
    private String id;
    private String name;
    private String description;
}
