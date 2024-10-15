package ru.yandex.practicum.filmorate.exceptions;

import java.util.Map;

public class ValidationException extends RuntimeException {
    private final Map<String, String> validationErrors;

    public ValidationException(Map<String, String> validationErrors) {
        super("Validation failed");
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
}
