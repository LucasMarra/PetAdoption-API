package com.animaladoption.petadoptionapi.service;

import com.animaladoption.petadoptionapi.repository.AnimalRepository;
import com.animaladoption.petadoptionapi.service.event.GetAnimalsEvent;
import com.animaladoption.petadoptionapi.service.event.GetAnimalsResultEvent;
import com.animaladoption.petadoptionapi.service.transformer.AnimalTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalTransformer animalTransformer;

    private static final Integer DEFAULT_PAGINATION_SIZE = 50;


    public GetAnimalsResultEvent getAnimals(GetAnimalsEvent getAnimalsEvent) {

        var term = getAnimalsEvent.getTerm().orElse(null);
        var category = getAnimalsEvent.getCategory().map(Enum::toString).orElse(null);
        var status = getAnimalsEvent.getStatus().map(Enum::toString).orElse(null);
        var createdAt = getAnimalsEvent.getCreatedAt().orElse(null);
        var pageIndex = getAnimalsEvent.getPageIndex().orElse(0);
        var pageSize = getAnimalsEvent.getPageSize().orElse(DEFAULT_PAGINATION_SIZE);

        var animalList = animalRepository.getAnimalsBy(term, category, status, createdAt, pageIndex, pageSize);
        var totalPages = animalRepository.getTotalPages(term, category, status, createdAt, pageSize);

        return animalTransformer.from(animalList, totalPages, pageIndex);
    }
}
