package com.animaladoption.petadoptionapi.repository.mapper;

import com.animaladoption.petadoptionapi.repository.entity.AnimalEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AnimalRowMapper implements RowMapper<AnimalEntity> {
    @Override
    public AnimalEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnimalEntity animal = new AnimalEntity();
        animal.setId(rs.getString("id"));
        animal.setExternalId(rs.getString("external_id"));
        animal.setName(rs.getString("name"));
        animal.setDescription(rs.getString("description"));
        animal.setImageUrl(rs.getString("image_url"));
        animal.setCategory(rs.getString("category"));
        animal.setStatus(rs.getString("status"));
        animal.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        animal.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return animal;
    }
}
