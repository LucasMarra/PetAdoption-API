package com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi.model;

import lombok.Data;

import java.util.List;

@Data
public class DogResultEventList {
    private List<DogResultEvent> breeds;
}
