package ru.yandex.practicum.filmorate.exceptions;

public class GenreValidationException extends RuntimeException {
    public GenreValidationException() {
    }

    public GenreValidationException(String message) {
        super(message);
    }
}
