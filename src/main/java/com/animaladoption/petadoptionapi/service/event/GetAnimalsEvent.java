package com.animaladoption.petadoptionapi.service.event;

import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.domain.AnimalStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
public class GetAnimalsEvent {
    private String term;
    private AnimalCategory category;
    private AnimalStatus status;
    private LocalDateTime createdAt;
    private Integer pageIndex;
    private Integer pageSize;

    public Optional<String> getTerm() {
        return Optional.ofNullable(term);
    }

    public Optional<AnimalCategory> getCategory() {
        return Optional.ofNullable(category);
    }

    public Optional<AnimalStatus> getStatus() {
        return Optional.ofNullable(status);
    }

    public Optional<LocalDateTime> getCreatedAt() {
        return Optional.ofNullable(createdAt);
    }

    public Optional<Integer> getPageIndex() {
        return Optional.ofNullable(pageIndex);
    }

    public Optional<Integer> getPageSize() {
        return Optional.ofNullable(pageSize);
    }
}
