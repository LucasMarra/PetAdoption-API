package com.animaladoption.petadoptionapi.web.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SyncResponse {
    private String operationId;
    private String message;
    private String status;

    public static SyncResponse getSyncResponseSyncing(){
        return SyncResponse.builder()
                .operationId(UUID.randomUUID().toString())
                .message("Animal indexing has started and is in progress")
                .status("SYNCING")
                .build();
    }

    public static SyncResponse getAbortSyncing(){
        return SyncResponse.builder()
                .message("Another synchronization is already in progress")
                .status("SYNCING")
                .build();
    }
}
