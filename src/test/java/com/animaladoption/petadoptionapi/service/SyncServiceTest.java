package com.animaladoption.petadoptionapi.service;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.domain.AnimalStatus;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi.CatApiClient;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi.DogApiClient;
import com.animaladoption.petadoptionapi.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;

class SyncServiceTest {

    private SyncService syncService;
    @Mock private CatApiClient catApiClient;
    @Mock private DogApiClient dogApiClient;
    @Mock private AnimalRepository animalRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        syncService = new SyncService(catApiClient ,dogApiClient, animalRepository);
    }

    @Test
    @DisplayName("Syncing animals successfully")
    void name() throws ExecutionException, InterruptedException {

        Animal cat1 = Animal.builder()
                .id("1")
                .externalId("cat1")
                .name("Cat 1")
                .description("Cat 1 description")
                .imageUrl("http://cat1.com")
                .category(AnimalCategory.CAT)
                .status(AnimalStatus.AVAILABLE)
                .build();

        Animal cat2 = Animal.builder()
                .id("2")
                .externalId("cat2")
                .name("Cat 2")
                .description("Cat 2 description")
                .imageUrl("http://cat2.com")
                .category(AnimalCategory.CAT)
                .status(AnimalStatus.AVAILABLE)
                .build();

        Animal dog1 = Animal.builder()
                .id("3")
                .externalId("dog1")
                .name("Dog 1")
                .description("Dog 1 description")
                .imageUrl("http://dog1.com")
                .category(AnimalCategory.DOG)
                .status(AnimalStatus.AVAILABLE)
                .build();

        Animal dog2 = Animal.builder()
                .id("4")
                .externalId("dog2")
                .name("Dog 2")
                .description("Dog 2 description")
                .imageUrl("http://dog2.com")
                .category(AnimalCategory.DOG)
                .status(AnimalStatus.AVAILABLE)
                .build();

        List<Animal> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);

        List<Animal> dogs = new ArrayList<>();
        dogs.add(dog1);
        dogs.add(dog2);

        List<Animal> animals = new ArrayList<>();
        animals.addAll(cats);
        animals.addAll(dogs);

        CompletableFuture<List<Animal>> catsFuture = CompletableFuture.completedFuture(cats);
        CompletableFuture<List<Animal>> dogsFuture = CompletableFuture.completedFuture(dogs);

        when(catApiClient.getAllAnimals()).thenReturn(catsFuture.get());
        when(dogApiClient.getAllAnimals()).thenReturn(dogsFuture.get());

        // when
        syncService.syncAnimals();

        // then
        verify(animalRepository, times(1)).save(anyList());
        verify(animalRepository, times(1)).save(animals);
    }
}