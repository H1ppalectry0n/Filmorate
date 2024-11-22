package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldPassValidationWithValidFilm() {
        Film film = new Film(1L, "Valid Name", "Valid Description",
                LocalDate.of(2000, 1, 1), 120, new MPARating(), null, null);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size(), "Expected no validation errors.");
    }

    @Test
    public void shouldFailValidationWhenNameIsBlank() {
        Film film = new Film(1L, "", "Valid Description",
                LocalDate.of(2000, 1, 1), 120, new MPARating(), null, null);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Name is required", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWhenDescriptionExceedsMaxLength() {
        String longDescription = "A".repeat(201); // Строка длиной 201 символ
        Film film = new Film(1L, "Valid Name", longDescription,
                LocalDate.of(2000, 1, 1), 120, new MPARating(), null, null);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Description cannot exceed 200 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWhenReleaseDateIsInFuture() {
        Film film = new Film(1L, "Valid Name", "Valid Description",
                LocalDate.now().plusDays(1), 120, new MPARating(), null, null);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Release date cannot be in the future", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWhenReleaseDateIsTooEarly() {
        Film film = new Film(1L, "Valid Name", "Valid Description",
                LocalDate.of(1800, 1, 1), 120, new MPARating(), null, null);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Release date cannot be earlier than December 28, 1895",
                violations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWhenDurationIsNotPositive() {
        Film film = new Film(1L, "Valid Name", "Valid Description",
                LocalDate.of(2000, 1, 1), -120, new MPARating(), null, null);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Duration must be positive", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWhenDurationIsNull() {
        Film film = new Film(1L, "Valid Name", "Valid Description",
                LocalDate.of(2000, 1, 1), null, new MPARating(), null, null);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Duration is required", violations.iterator().next().getMessage());
    }

}