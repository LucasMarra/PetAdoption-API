package com.animaladoption.petadoptionapi.service;

import com.animaladoption.petadoptionapi.domain.Animal;

import java.util.List;

public interface AnimalConnector {
    List<Animal> getAllAnimals();
}
