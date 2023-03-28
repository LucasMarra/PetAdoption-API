package com.animaladoption.petadoptionapi.web.controller;

import com.animaladoption.petadoptionapi.service.SyncService;
import com.animaladoption.petadoptionapi.web.controller.dto.SyncResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SyncControllerTest {

    @Mock private SyncService syncService;
    private SyncController syncController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        syncController = new SyncController(syncService);
    }


    @Test
    @DisplayName("Should return a 202 Accepted response")
     void testSync_shouldReturnAccepted() {
        ResponseEntity<SyncResponse> response = syncController.sync();
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return a 409 Conflict response if syncing is in progress")
    void testSync_shouldReturnConflictIfSyncInProgress() {
        // Arrange
        when(syncService.isIndexingInProgress()).thenReturn(true);
        SyncController syncController = new SyncController(syncService);

        // Act
        ResponseEntity<SyncResponse> response = syncController.sync();

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}