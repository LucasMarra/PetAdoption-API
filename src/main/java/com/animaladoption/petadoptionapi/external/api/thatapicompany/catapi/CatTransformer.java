package com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi.model.CatBreedResultEvent;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi.model.CatResultEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatTransformer {

    public List<Animal> from (List<CatResultEvent> catResultEventList) {
        return catResultEventList.stream()
                .map(catResultEvent -> {

                    var breed = catResultEvent.getBreeds().stream().findFirst();

                    return Animal.builder()
                            .externalId(catResultEvent.getId())
                            .name(breed.map(CatBreedResultEvent::getName).orElse(null))
                            .description(breed.map(CatBreedResultEvent::getDescription).orElse(null))
                            .imageUrl(catResultEvent.getUrl())
                            .category(AnimalCategory.CAT)
                            .build();
                })
                .toList();
    }


}
