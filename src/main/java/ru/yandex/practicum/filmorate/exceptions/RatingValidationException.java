package ru.yandex.practicum.filmorate.exceptions;

public class RatingValidationException extends RuntimeException {
    public RatingValidationException() {
    }

    public RatingValidationException(String message) {
        super(message);
    }
}
