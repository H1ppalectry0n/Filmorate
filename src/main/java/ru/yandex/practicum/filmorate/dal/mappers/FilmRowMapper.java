package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("id"));                   // Установка ID фильма
        film.setName(rs.getString("name"));             // Установка имени
        film.setDescription(rs.getString("description")); // Установка описания
        film.setReleaseDate(rs.getDate("releaseDate").toLocalDate()); // Установка даты релиза
        film.setDuration(rs.getInt("duration"));        // Установка продолжительности
        film.setMpa(new MPARating(rs.getLong("rating_id"), rs.getString("rating_name")));      // Установка ID рейтинга
        return film;
    }
}
