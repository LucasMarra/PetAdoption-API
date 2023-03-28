package com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi.model.DogBreedResultEvent;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi.model.DogResultEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DogTransformerTest {
    
    private DogTransformer dogTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dogTransformer = new DogTransformer();
    }

    @Test
    @DisplayName("from() should return an empty list when dogResultEventList is empty")
    void dogTransformerTest01() {
        // Arrange
        List<DogResultEvent> dogResultEventList = new ArrayList<>();

        // Act
        List<Animal> result = dogTransformer.from(dogResultEventList);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("from() should return a list of animals with mapped values")
    void dogTransformerTest02() {
        // Arrange
        DogResultEvent dog1 = new DogResultEvent("1", "http://dog1.com", List.of(new DogBreedResultEvent("1","Golden Retriever", "Friendly and intelligent.")));
        DogResultEvent dog2 = new DogResultEvent("2", "http://dog2.com", List.of(new DogBreedResultEvent("2","Labrador Retriever", "Kind, pleasant, and outgoing.")));
        List<DogResultEvent> dogResultEventList = List.of(dog1, dog2);

        // Act
        List<Animal> result = dogTransformer.from(dogResultEventList);

        // Assert
        assertEquals(2, result.size());

        Animal animal1 = result.get(0);
        assertEquals("1", animal1.getExternalId());
        assertEquals("Golden Retriever", animal1.getName());
        assertEquals("Friendly and intelligent.", animal1.getDescription());
        assertEquals("http://dog1.com", animal1.getImageUrl());
        assertEquals(AnimalCategory.DOG, animal1.getCategory());

        Animal animal2 = result.get(1);
        assertEquals("2", animal2.getExternalId());
        assertEquals("Labrador Retriever", animal2.getName());
        assertEquals("Kind, pleasant, and outgoing.", animal2.getDescription());
        assertEquals("http://dog2.com", animal2.getImageUrl());
        assertEquals(AnimalCategory.DOG, animal2.getCategory());
    }


}