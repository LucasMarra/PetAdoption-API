package com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatBreedResultEvent {
    private String id;
    private String name;
    private String description;
}
