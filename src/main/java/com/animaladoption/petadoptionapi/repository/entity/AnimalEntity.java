package com.animaladoption.petadoptionapi.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnimalEntity {
    private String id;
    private String externalId;
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private String status;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
