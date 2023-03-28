package com.animaladoption.petadoptionapi.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
public class CatApiConfig {
    @Getter
    private final String url;
    @Getter
    private final String apiKey;

    public CatApiConfig(Environment environment) {
        log.info("Loading Cat's API configurations...");
        url = environment.getProperty("externals.cat-api.url");
        apiKey = environment.getProperty("externals.cat-api.x-api-key");
    }
}
