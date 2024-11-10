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

class UserTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldPassValidationWithValidUser() {
        User user = new User(1, "valid.email@example.com", "validLogin", "Valid Name",
                LocalDate.of(2000, 1, 1), null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "Expected no validation errors.");
    }

    @Test
    public void shouldFailValidationWhenEmailIsInvalid() {
        User user = new User(1, "invalid-email", "validLogin", "Valid Name",
                LocalDate.of(2000, 1, 1), null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Email should be valid", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWhenEmailIsBlank() {
        User user = new User(1, "", "validLogin", "Valid Name",
                LocalDate.of(2000, 1, 1), null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Email is required", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWhenLoginIsBlank() {
        User user = new User(1, "valid.email@example.com", "", "Valid Name",
                LocalDate.of(2000, 1, 1), null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Login cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWhenLoginContainsSpaces() {
        User user = new User(1, "valid.email@example.com", "invalid login", "Valid Name",
                LocalDate.of(2000, 1, 1), null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Login cannot contain spaces", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldPassValidationWhenNameIsNull() {
        User user = new User(1, "valid.email@example.com", "validLogin", null,
                LocalDate.of(2000, 1, 1), null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "Expected no validation errors for null name.");
    }

    @Test
    public void shouldFailValidationWhenBirthdayIsInFuture() {
        User user = new User(1, "valid.email@example.com", "validLogin", "Valid Name",
                LocalDate.now().plusDays(1), null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Birthday cannot be in the future", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWhenBirthdayIsNull() {
        User user = new User(1, "valid.email@example.com", "validLogin", "Valid Name",
                null, null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Birthday is required", violations.iterator().next().getMessage());
    }

}