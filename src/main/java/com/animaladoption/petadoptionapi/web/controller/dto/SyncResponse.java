package com.animaladoption.petadoptionapi.web.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncResponse {
    private String operationId;
    private String message;
    private String status;

    public static SyncResponse getSyncResponseSyncing(){
        return SyncResponse.builder()
                .operationId(UUID.randomUUID().toString())
                .message("A indexação de animais foi iniciada e está em andamento")
                .status("SYNCING")
                .build();
    }

    public static SyncResponse getAbortSyncing(){
        return SyncResponse.builder()
                .message("Outra sincronização já está em andamento.")
                .status("SYNCING")
                .build();
    }
}
