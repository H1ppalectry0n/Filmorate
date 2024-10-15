package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    private final Map<Integer, Film> films = new HashMap<>();
    private int filmIdCounter = 1;

    // Add a new film
    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Validation failed for adding film: {}", result.getFieldErrors());
            throw new ValidationException(collectValidationErrors(result));
        }
        film.setId(filmIdCounter++);
        films.put(film.getId(), film);
        logger.info("Film added successfully: {}", film);
        return new ResponseEntity<>(film, HttpStatus.CREATED);
    }

    // Update an existing film
    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film updatedFilm, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Validation failed for updating film: {}", result.getFieldErrors());
            throw new ValidationException(collectValidationErrors(result));
        }
        if (updatedFilm.getId() == null || !films.containsKey(updatedFilm.getId())) {
            logger.warn("Film not found with ID: {}", updatedFilm.getId());
            return new ResponseEntity<Film>(updatedFilm, HttpStatus.NOT_FOUND);
        }
        films.put(updatedFilm.getId(), updatedFilm);
        logger.info("Film updated successfully: {}", films.get(updatedFilm.getId()));
        return ResponseEntity.ok(updatedFilm);
    }

    // Get all films
    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        logger.info("Retrieving all films");
        return ResponseEntity.ok(new ArrayList<>(films.values()));
    }

    // Helper method to collect validation errors
    private Map<String, String> collectValidationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
