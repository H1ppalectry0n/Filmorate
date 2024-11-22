package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmRepository.class, FilmRowMapper.class})
class FilmRepositoryTest {
    private final FilmRepository filmRepository;

    @Test
    void testFindAllFilms() {
        List<Film> films = filmRepository.findAll();

        Assertions.assertFalse(films.isEmpty(), "Film list should not be empty");
        Assertions.assertNotNull(films.get(0).getId(), "First film ID should not be null");
        Assertions.assertEquals("Inception", films.get(0).getName(), "First film name should match");
    }

    @Test
    void testFindFilmById() {
        Optional<Film> film = filmRepository.findById(2L);

        Assertions.assertTrue(film.isPresent(), "Film with ID 1 should be present");
        Assertions.assertEquals("The Matrix", film.get().getName(), "Film name should match");
    }

    @Test
    void testSaveFilm() {
        Film film = new Film();
        film.setName("New Film");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(120);
        film.setMpa(new MPARating(1L, "G"));

        Film savedFilm = filmRepository.save(film);

        Assertions.assertNotNull(savedFilm.getId(), "Saved film ID should not be null");
        Assertions.assertEquals("New Film", savedFilm.getName(), "Saved film name should match");
    }

    @Test
    void testUpdateFilm() {
        Optional<Film> film = filmRepository.findById(1L);

        Assertions.assertTrue(film.isPresent(), "Film with ID 1 should be present");

        film.ifPresent(f -> {
            f.setName("Updated Name");
            filmRepository.update(f);

            Optional<Film> updatedFilm = filmRepository.findById(1L);
            Assertions.assertTrue(updatedFilm.isPresent(), "Updated film should be present");
            Assertions.assertEquals("Updated Name", updatedFilm.get().getName(), "Updated film name should match");
        });
    }

}