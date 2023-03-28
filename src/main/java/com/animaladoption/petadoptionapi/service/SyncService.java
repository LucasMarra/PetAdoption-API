package com.animaladoption.petadoptionapi.service;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi.CatApiClient;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi.DogApiClient;
import com.animaladoption.petadoptionapi.repository.AnimalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
@Slf4j
public class SyncService {

    private final AtomicBoolean isIndexingInProgress = new AtomicBoolean(false);
    private final CatApiClient catApiClient;
    private final DogApiClient dogApiClient;
    private final AnimalRepository animalRepository;

    public boolean isIndexingInProgress(){
        return isIndexingInProgress.get();
    }


    public void syncAnimals() {
        log.info("Initializing sync process");
        isIndexingInProgress.set(true);

        var animalsToSave = new ArrayList<Animal>();
        var dogs = dogApiClient.getAllAnimals();
        var cats = catApiClient.getAllAnimals();
        animalsToSave.addAll(cats);
        animalsToSave.addAll(dogs);

        animalRepository.save(animalsToSave);

        isIndexingInProgress.set(false);
        log.info("Finished syncing process successfully");
    }
}
