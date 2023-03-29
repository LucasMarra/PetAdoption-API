package com.animaladoption.petadoptionapi.repository.mapper;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.domain.AnimalStatus;
import com.animaladoption.petadoptionapi.repository.entity.AnimalEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class AnimalMapper {
    public List<Animal> from(List<AnimalEntity> animalEntities) {
        return animalEntities.stream()
                .map(animalEntity -> Animal.builder()
                        .id(animalEntity.getId())
                        .externalId(animalEntity.getExternalId())
                        .name(animalEntity.getName())
                        .description(animalEntity.getDescription())
                        .imageUrl(animalEntity.getImageUrl())
                        .category(AnimalCategory.valueOf(animalEntity.getCategory()))
                        .status(AnimalStatus.valueOf(animalEntity.getStatus()))
                        .createdAt(animalEntity.getCreatedAt())
                        .updatedAt(animalEntity.getUpdatedAt())
                        .build())
                .toList();
    }

    public Optional<Animal> from(AnimalEntity animalEntity) {

        if (isNull(animalEntity)) return Optional.empty();

        return Optional.of(Animal.builder()
                .id(animalEntity.getId())
                .externalId(animalEntity.getExternalId())
                .name(animalEntity.getName())
                .description(animalEntity.getDescription())
                .imageUrl(animalEntity.getImageUrl())
                .category(AnimalCategory.valueOf(animalEntity.getCategory()))
                .status(AnimalStatus.valueOf(animalEntity.getStatus()))
                .createdAt(animalEntity.getCreatedAt())
                .updatedAt(animalEntity.getUpdatedAt())
                .build());
    }
}
