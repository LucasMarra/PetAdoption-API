package com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi.model.DogBreedResultEvent;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi.model.DogResultEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogTransformer {

    public List<Animal> from (List<DogResultEvent> dogResultEventList) {
        return dogResultEventList.stream()
                .map(catResultEvent -> {

                    var breed = catResultEvent.getBreeds().stream().findFirst();

                    return Animal.builder()
                            .externalId(catResultEvent.getId())
                            .name(breed.map(DogBreedResultEvent::getName).orElse(null))
                            .description(breed.map(DogBreedResultEvent::getDescription).orElse(null))
                            .imageUrl(catResultEvent.getUrl())
                            .category(AnimalCategory.DOG)
                            .build();
                })
                .toList();
    }


}
