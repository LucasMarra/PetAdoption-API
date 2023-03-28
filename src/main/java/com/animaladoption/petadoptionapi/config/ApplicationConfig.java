package com.animaladoption.petadoptionapi.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {
    @Bean
    public DataSource dataSource(DataSourceConfig dataSourceConfig) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(dataSourceConfig.getUrl());
        dataSourceBuilder.username(dataSourceConfig.getUsername());
        dataSourceBuilder.password(dataSourceConfig.getPassword());
        return dataSourceBuilder.build();
    }
}
