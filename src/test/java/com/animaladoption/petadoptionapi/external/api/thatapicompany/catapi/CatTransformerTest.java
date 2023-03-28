package com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi.model.CatBreedResultEvent;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi.model.CatResultEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CatTransformerTest {
    private CatTransformer catTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        catTransformer = new CatTransformer();
    }

    @Test
    @DisplayName("from() should return an empty list when catResultEventList is empty")
    void catTransformerTest01() {
        // Arrange
        List<CatResultEvent> catResultEventList = new ArrayList<>();

        // Act
        List<Animal> result = catTransformer.from(catResultEventList);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("from() should return a list of animals with mapped values")
    void catTransformerTest02() {
        // Arrange
        CatResultEvent cat1 = new CatResultEvent("1", "http://cat1.com", List.of(new CatBreedResultEvent("1","Cat 1", "Happy.")));
        CatResultEvent cat2 = new CatResultEvent("2", "http://cat2.com", List.of(new CatBreedResultEvent("2","Cat 2", "Evil.")));
        List<CatResultEvent> catResultEventList = List.of(cat1, cat2);

        // Act
        List<Animal> result = catTransformer.from(catResultEventList);

        // Assert
        assertEquals(2, result.size());

        Animal animal1 = result.get(0);
        assertEquals("1", animal1.getExternalId());
        assertEquals("Cat 1", animal1.getName());
        assertEquals("Happy.", animal1.getDescription());
        assertEquals("http://cat1.com", animal1.getImageUrl());
        assertEquals(AnimalCategory.CAT, animal1.getCategory());

        Animal animal2 = result.get(1);
        assertEquals("2", animal2.getExternalId());
        assertEquals("Cat 2", animal2.getName());
        assertEquals("Evil.", animal2.getDescription());
        assertEquals("http://cat2.com", animal2.getImageUrl());
        assertEquals(AnimalCategory.CAT, animal2.getCategory());
    }

}