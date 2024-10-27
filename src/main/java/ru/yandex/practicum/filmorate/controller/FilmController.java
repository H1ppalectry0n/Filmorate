package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmOrUserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;
    private final FilmService filmService;

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> likeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        final Film film = filmStorage.getFilmById(id);
        final User user = userStorage.getUserById(userId);

        if (film == null) {
            throw new FilmOrUserNotFoundException("Фильм не существует");
        }

        if (user == null) {
            throw new FilmOrUserNotFoundException("Пользователь не существует");
        }

        filmService.addLike(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        final Film film = filmStorage.getFilmById(id);
        final User user = userStorage.getUserById(userId);

        if (film == null) {
            throw new FilmOrUserNotFoundException("Фильм не существует");
        }

        if (user == null) {
            throw new FilmOrUserNotFoundException("Пользователь не существует");
        }

        filmService.removeLike(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/popular")
    public List<Film> getMostLikeFilms(@RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {
        return filmService.getPopularFilms(count);
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(collectValidationErrors(result));
        }
        Film savedFilm = filmStorage.addFilm(film);
        return new ResponseEntity<>(savedFilm, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film updatedFilm, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(collectValidationErrors(result));
        }
        Film updated = filmStorage.updateFilm(updatedFilm);
        if (updated == null) {
            return new ResponseEntity<>(updatedFilm, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok(filmStorage.getAllFilms());
    }

    private Map<String, String> collectValidationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
