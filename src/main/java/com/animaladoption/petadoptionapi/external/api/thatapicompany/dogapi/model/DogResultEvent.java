package com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogResultEvent {
    private String id;
    private String url;
    private List<DogBreedResultEvent> breeds;
}
