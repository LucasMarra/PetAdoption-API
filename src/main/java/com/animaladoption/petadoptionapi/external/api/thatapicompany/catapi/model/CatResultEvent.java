package com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatResultEvent {
    private String id;
    private String url;
    private List<CatBreedResultEvent> breeds;
}
