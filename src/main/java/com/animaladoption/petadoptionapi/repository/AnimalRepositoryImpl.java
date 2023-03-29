package com.animaladoption.petadoptionapi.repository;

import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.domain.AnimalStatus;
import com.animaladoption.petadoptionapi.repository.mapper.AnimalMapper;
import com.animaladoption.petadoptionapi.repository.mapper.AnimalRowMapper;
import com.animaladoption.petadoptionapi.util.SqlUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Repository
@Slf4j
@AllArgsConstructor
public class AnimalRepositoryImpl implements AnimalRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final AnimalMapper animalMapper;
    private final AnimalRowMapper animalRowMapper;

    private static final String UPSERT_ANIMAL_SQL = SqlUtils.loadSql("/animal/upsert_animal.sql");
    private static final int BATCH_SIZE = 1000;

    @Override
    public void save(List<Animal> animals) {

        log.debug("Inserting {} animals into db", animals.size());

        //Batch inserts animals in chunks of 1k to avoid overwhelming the database with a large transaction.

        IntStream.range(0, (animals.size() + BATCH_SIZE - 1) / BATCH_SIZE)
                .mapToObj(i -> animals.subList(i * BATCH_SIZE, Math.min((i + 1) * BATCH_SIZE, animals.size())))
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
                                ps.setString(7, AnimalStatus.AVAILABLE.toString());
                                ps.setObject(8, LocalDateTime.now());
                                ps.setObject(9, LocalDateTime.now());
                            }

                            @Override
                            public int getBatchSize() {
                                return batch.size();
                            }
                        }));
    }

    @Override
    public Integer getTotalPages(String name, String category, String status, LocalDateTime createdAt, Integer pageSize) {
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM tb_animal WHERE 1=1");
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (name != null) {
            query.append(" AND name LIKE :name");
            params.addValue("name", "%" + name + "%");
        }

        if (category != null) {
            query.append(" AND category = :category");
            params.addValue("category", category);
        }

        if (status != null) {
            query.append(" AND status = :status");
            params.addValue("status", status);
        }

        if (createdAt != null) {
            query.append(" AND created_at >= :created_at");
            params.addValue("created_at", createdAt);
        }

        var totalRecordsOpt =
                Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query.toString(), params, Integer.class));
        int totalRecords = totalRecordsOpt.orElse(0);
        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    public List<Animal> getAnimalsBy(String term, String category, String status, LocalDateTime createdAt, Integer pageIndex, Integer pageSize) {
        StringBuilder query = new StringBuilder("SELECT * FROM tb_animal WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (term != null) {
            query.append(" AND (name LIKE ? OR description LIKE ?)");
            params.add("%" + term + "%");
            params.add("%" + term + "%");
        }

        if (category != null) {
            query.append(" AND category = ?");
            params.add(category);
        }

        if (status != null) {
            query.append(" AND status = ?");
            params.add(status);
        }

        if (createdAt != null) {
            query.append(" AND created_at >= ?");
            params.add(createdAt);
        }

        query.append(" ORDER BY created_at DESC");
        query.append(" LIMIT ?");
        query.append(" OFFSET ?");
        params.add(pageSize);
        params.add(pageIndex * pageSize);

        var animalEntities =  jdbcTemplate.query(query.toString(), animalRowMapper, params.toArray());
        return animalMapper.from(animalEntities);
    }

    @Override
    public Optional<Animal> updateStatus(String idAnimal, AnimalStatus status) {
        log.debug("Updating idAnimal {} to status {} ", idAnimal, status);
        String sql = "UPDATE tb_animal SET status = ? WHERE id = ? RETURNING *";
        try {
            var animalEntity = jdbcTemplate.queryForObject(sql, animalRowMapper, status.toString(), idAnimal);
            return animalMapper.from(animalEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}

