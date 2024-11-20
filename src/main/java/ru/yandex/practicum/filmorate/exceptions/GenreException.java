package ru.yandex.practicum.filmorate.exceptions;

public class GenreException extends RuntimeException {
    public GenreException() {
    }

    public GenreException(String message) {
        super(message);
    }
}
