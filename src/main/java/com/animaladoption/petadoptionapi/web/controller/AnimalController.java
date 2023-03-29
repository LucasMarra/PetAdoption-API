package com.animaladoption.petadoptionapi.web.controller;

import com.animaladoption.petadoptionapi.service.AnimalService;
import com.animaladoption.petadoptionapi.web.controller.converter.AnimalConverter;
import com.animaladoption.petadoptionapi.web.controller.dto.AnimalResponse;
import com.animaladoption.petadoptionapi.web.controller.dto.AnimalStatusChangeRequest;
import com.animaladoption.petadoptionapi.web.controller.validation.AnimalControllerRequestValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalConverter animalConverter;
    private final AnimalControllerRequestValidation requestValidation;


    @GetMapping(path = "/animal")
    public ResponseEntity<List<AnimalResponse>> listAll(
            @RequestParam Map<String, String> params
    ) {
        // Validate request
        requestValidation.validateGetAnimalsRequest(params);

        // Process request
        var animalGetEvent = animalConverter.from(params);
        var animalsResultEvent = animalService.getAnimals(animalGetEvent);

        // Parse and return responses
        var responseHeaders = animalConverter.responseHeadersFrom(animalsResultEvent);

        if (animalsResultEvent.getAnimalList().isEmpty()) return ResponseEntity.noContent()
                .headers(responseHeaders)
                .build();

        var animalResponse = animalConverter.from(animalsResultEvent);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(animalResponse);

    }

    @PatchMapping("animal/{idAnimal}/status")
    public ResponseEntity<?> updateAnimalStatus(
            @PathVariable String idAnimal, @RequestBody AnimalStatusChangeRequest statusRequest
    ) {
        requestValidation.validateUpdateAnimalStatusRequest(statusRequest.getStatus());
        var animalStatus = animalConverter.from(statusRequest.getStatus());
        var animal = animalService.updateStatus(idAnimal, animalStatus);

        return animal.map(unused -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }
}
