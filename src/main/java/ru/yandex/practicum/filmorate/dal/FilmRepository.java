package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> {
    private static final String FIND_ALL_QUERY = "SELECT films.id, films.name, films.description, films.releaseDate, films.duration, films.rating_id, ratings.name AS rating_name FROM films JOIN ratings ON films.rating_id = ratings.id;";
    private static final String FIND_BY_ID = "SELECT films.id, films.name, films.description, films.releaseDate, films.duration, films.rating_id, ratings.name AS rating_name FROM films JOIN ratings ON films.rating_id = ratings.id WHERE films.id = ?;";
    private static final String INSERT_QUERY = "INSERT INTO films (name, description, releaseDate, duration, rating_id) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, releaseDate = ?, duration = ? WHERE id = ?;";
    private static final String ADD_FAVORITE_QUERY = "INSERT INTO films_favorites (film_id, user_id) VALUES (?, ?);";
    private static final String REMOVE_FAVORITE_QUERY = "DELETE FROM films_favorites WHERE film_id = ? AND user_id = ?;";

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public List<Film> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<Film> findById(Long id) {
        return findOne(FIND_BY_ID, id);
    }

    public Film save(Film film) {
        Long id = insert(INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());
        film.setId(id);

        // Добавление в таблицу жанров
        String sql = "INSERT INTO films_genre(film_id, genre_id) VALUES (?, ?);";
        for (Genre genre : new HashSet<>(film.getGenres())) {
            insertMtM(sql, id, genre.getId());
        }

        return film;
    }

    public Film update(Film film) {
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId()
        );
        return film;
    }

    public void addFavorite(Long filmId, Long userId) {
        insertMtM(ADD_FAVORITE_QUERY, filmId, userId);
    }

    public void removeFavorite(Long filmId, Long userId) {
        delete(REMOVE_FAVORITE_QUERY, filmId, userId);
    }
}
