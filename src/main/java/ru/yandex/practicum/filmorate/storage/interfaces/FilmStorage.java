package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    boolean deleteFilm(int id);

    List<Film> getAllFilms();

    Film getFilmById(int id);
}
