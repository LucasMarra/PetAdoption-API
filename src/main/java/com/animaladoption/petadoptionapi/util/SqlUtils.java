package com.animaladoption.petadoptionapi.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
public class SqlUtils {

    public static String loadSql(String sql) {
        try {
            String filePath = "db/sql" + sql;
            InputStream is = ResourceLoader.class.getClassLoader().getResourceAsStream(filePath);
            return new String(is != null ? is.readAllBytes() : new byte[0], StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error to load SQL File {}", e.getMessage());
            throw new IllegalArgumentException("Unable to load sql file: " +  sql);
        }
    }

}
