package com.animaladoption.petadoptionapi.service.event;

import com.animaladoption.petadoptionapi.domain.Animal;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAnimalsResultEvent {
    private List<Animal> animalList;
    private Integer totalPages;
    private Integer currentPage;
    private Integer nextPage;
}
