package ru.yandex.practicum.filmorate.exceptions;

public class RatingExeption extends RuntimeException {
    public RatingExeption() {
    }

    public RatingExeption(String message) {
        super(message);
    }
}
