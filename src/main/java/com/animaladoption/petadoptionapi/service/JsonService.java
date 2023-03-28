package com.animaladoption.petadoptionapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
@Service
public class JsonService {

    private final ObjectMapper mapper;

    public <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
        log.debug("Parsing json to [{}]", valueTypeRef.getType().getTypeName());
        if (json == null) return null;
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (JsonMappingException e) {
            log.error("Unable to deserialize JSON | {} | to class |{}|", json, valueTypeRef, e);

            throw new IllegalArgumentException("INVALID_JSON");
        } catch (IOException e) {
            log.error("Unable to deserialize JSON | {} | to class |{}|", json, valueTypeRef, e);
            if(e.getMessage().contains("Unexpected character")){
                throw new IllegalArgumentException("INVALID_JSON");
            }
            throw new RuntimeException(e);
        }
    }

}
