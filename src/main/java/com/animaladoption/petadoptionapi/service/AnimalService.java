package com.animaladoption.petadoptionapi.service;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.domain.AnimalStatus;
import com.animaladoption.petadoptionapi.repository.AnimalRepository;
import com.animaladoption.petadoptionapi.service.event.GetAnimalsEvent;
import com.animaladoption.petadoptionapi.service.event.GetAnimalsResultEvent;
import com.animaladoption.petadoptionapi.service.transformer.AnimalTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalTransformer animalTransformer;

    private static final Integer DEFAULT_PAGINATION_SIZE = 50;


    public Optional<Animal> updateStatus(String animalId, AnimalStatus animalStatus) {
        log.info("Updating animal status to {} for animalId {}", animalStatus, animalId);
        return animalRepository.updateStatus(animalId, animalStatus);
    }


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
