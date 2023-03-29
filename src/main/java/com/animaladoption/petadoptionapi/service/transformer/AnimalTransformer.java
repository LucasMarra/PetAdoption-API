package com.animaladoption.petadoptionapi.service.transformer;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.service.event.GetAnimalsResultEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalTransformer {

    public GetAnimalsResultEvent from (List<Animal> animalList, Integer totalPages, Integer currentPage) {
        return GetAnimalsResultEvent.builder()
                .animalList(animalList)
                .totalPages(totalPages)
                .currentPage(currentPage)
                .nextPage(currentPage + 1)
                .build();
    }
}
