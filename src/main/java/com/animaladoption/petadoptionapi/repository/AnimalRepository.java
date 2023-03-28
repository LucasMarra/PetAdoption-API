package com.animaladoption.petadoptionapi.repository;

import com.animaladoption.petadoptionapi.domain.Animal;

import java.util.List;

public interface AnimalRepository {

    void save(List<Animal> animalList);
}
