package com.animaladoption.petadoptionapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
@Slf4j
public class HttpService {

    private final OkHttpClient client;

    public Response makeCall(Request request) {
        log.debug("Making request | {}", request);
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBodyFrom(Response response) {
        if (nonNull(response.body())) {
            try {
                return response.body().string();
            } catch (Exception e) {
                log.error("Error getting body from response [{}]", response, e);
                throw new RuntimeException(e);
            }
        } else return null;
    }

}
