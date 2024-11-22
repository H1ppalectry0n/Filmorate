package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RatingRowMapper implements RowMapper<MPARating> {

    @Override
    public MPARating mapRow(ResultSet rs, int rowNum) throws SQLException {
        MPARating rating = new MPARating();
        rating.setId(rs.getLong("id"));       // Установка ID рейтинга
        rating.setName(rs.getString("name")); // Установка имени рейтинга
        return rating;
    }
}
