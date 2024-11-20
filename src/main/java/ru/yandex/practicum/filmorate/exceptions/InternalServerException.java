package ru.yandex.practicum.filmorate.exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException() {
    }

    public InternalServerException(String message) {
        super(message);
    }
}
