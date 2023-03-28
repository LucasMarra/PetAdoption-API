package com.animaladoption.petadoptionapi.repository;

import com.animaladoption.petadoptionapi.domain.AnimaStatus;
import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.util.SqlUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Repository
@Slf4j
@AllArgsConstructor
public class AnimalRepositoryImpl implements AnimalRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String UPSERT_ANIMAL_SQL = SqlUtils.loadSql("/animal/upsert_animal.sql");
    private static int batchSize = 1000;

    @Override
    public void save(List<Animal> animals) {

        log.debug("Inserting {} animals into db", animals.size());

        //Batch inserts animals in chunks of 1k to avoid overwhelming the database with a large transaction.

        IntStream.range(0, (animals.size() + batchSize - 1) / batchSize)
                .mapToObj(i -> animals.subList(i * batchSize, Math.min((i + 1) * batchSize, animals.size())))
                .forEach(batch -> jdbcTemplate.batchUpdate(UPSERT_ANIMAL_SQL, new BatchPreparedStatementSetter() {

                            @Override
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                Animal animal = animals.get(i);
                                ps.setString(1, UUID.randomUUID().toString());
                                ps.setString(2, animal.getExternalId());
                                ps.setString(3, animal.getName());
                                ps.setString(4, animal.getDescription());
                                ps.setString(5, animal.getImageUrl());
                                ps.setString(6, animal.getCategory().toString());
                                ps.setString(7, AnimaStatus.DISPONIVEL.toString());
                                ps.setObject(8, LocalDateTime.now());
                                ps.setObject(9, LocalDateTime.now());
                            }

                            @Override
                            public int getBatchSize() {
                                return batch.size();
                            }
                        }));
    }
}
