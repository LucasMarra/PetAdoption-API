package com.animaladoption.petadoptionapi.web.controller;

import com.animaladoption.petadoptionapi.service.SyncService;
import com.animaladoption.petadoptionapi.web.controller.dto.SyncResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
public class SyncController {

    private final SyncService syncService;

    @PostMapping(path = "/sync")
    public ResponseEntity<SyncResponse> sync() {

        if (syncService.isIndexingInProgress())
            return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(SyncResponse.getAbortSyncing());

        CompletableFuture.runAsync(syncService::syncAnimals);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(SyncResponse.getSyncResponseSyncing());
    }
}
