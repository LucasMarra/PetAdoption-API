package com.animaladoption.petadoptionapi.repository;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.domain.AnimalStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnimalRepository {

    void save(List<Animal> animalList);

    Integer getTotalPages(String name, String category, String status, LocalDateTime createdAt, Integer pageSize);

    List<Animal> getAnimalsBy(String term, String category, String status, LocalDateTime updatedAt, Integer pageIndex, Integer pageSize);

    Optional<Animal> updateStatus(String idAnimal, AnimalStatus status);

}
