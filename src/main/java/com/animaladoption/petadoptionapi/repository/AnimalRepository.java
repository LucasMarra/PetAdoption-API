package com.animaladoption.petadoptionapi.repository;

import com.animaladoption.petadoptionapi.domain.Animal;

import java.time.LocalDateTime;
import java.util.List;

public interface AnimalRepository {

    void save(List<Animal> animalList);

    Integer getTotalPages(String name, String category, String status, LocalDateTime createdAt, Integer pageSize);

    List<Animal> getAnimalsBy(String term, String category, String status, LocalDateTime updatedAt, Integer pageIndex, Integer pageSize);

}
