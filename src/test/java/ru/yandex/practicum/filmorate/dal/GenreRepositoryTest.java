package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GenreRepository.class, GenreRowMapper.class})
class GenreRepositoryTest {
    private final GenreRepository genreRepository;

    @Test
    void testFindAllGenres() {
        List<Genre> genres = genreRepository.findAll();

        Assertions.assertFalse(genres.isEmpty(), "Genre list should not be empty");
        Assertions.assertEquals(1L, genres.get(0).getId(), "First genre ID should match");
        Assertions.assertEquals("Комедия", genres.get(0).getName(), "First genre name should match");
    }

    @Test
    void testFindGenreById() {
        Genre genre = genreRepository.findById(1L);

        Assertions.assertNotNull(genre, "Genre with ID 1 should not be null");
        Assertions.assertEquals("Комедия", genre.getName(), "Genre name should match");
    }

}