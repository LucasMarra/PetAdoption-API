package com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi.model;

import lombok.Data;

import java.util.List;

@Data
public class CatResultEventList {
    private List<CatResultEvent> breeds;
}
