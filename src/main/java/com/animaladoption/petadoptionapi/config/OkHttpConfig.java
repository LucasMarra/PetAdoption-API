package com.animaladoption.petadoptionapi.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpConfig {
    @Bean
    public OkHttpClient httpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.MINUTES)
                .writeTimeout(20, TimeUnit.MINUTES)
                .callTimeout(20, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.MINUTES)
                .build();
    }
}
