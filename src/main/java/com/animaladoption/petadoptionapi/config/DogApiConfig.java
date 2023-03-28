package com.animaladoption.petadoptionapi.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
public class DogApiConfig {
    @Getter
    private final String url;
    @Getter
    private final String apiKey;

    public DogApiConfig(Environment environment) {
        log.info("Loading Cat's API configurations...");
        url = environment.getProperty("externals.dog-api.url");
        apiKey = environment.getProperty("externals.dog-api.x-api-key");
    }
}
