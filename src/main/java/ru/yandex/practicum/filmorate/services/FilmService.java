package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;

    public void addLike(int filmId, int userId) {
        final Film film = filmStorage.getFilmById(filmId);
        final User user = userStorage.getUserById(userId);

        if (film == null) {
            throw new FilmNotFoundException("Фильм не существует");
        }

        if (user == null) {
            throw new UserNotFoundException("Пользователь не существует");
        }

        Set<Integer> likes = film.getFavorites();
        likes.add(userId);
        film.setFavorites(likes);
    }

    public void removeLike(int filmId, int userId) {
        final Film film = filmStorage.getFilmById(filmId);
        final User user = userStorage.getUserById(userId);

        if (film == null) {
            throw new FilmNotFoundException("Фильм не существует");
        }

        if (user == null) {
            throw new UserNotFoundException("Пользователь не существует");
        }

        Set<Integer> likes = film.getFavorites();
        likes.remove(userId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getAllFilms().stream()
                .sorted((film, other) -> Integer.compare(other.getFavorites().size(), film.getFavorites().size()))
                .limit(count)
                .toList();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film updatedFilm) {
        return filmStorage.updateFilm(updatedFilm);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }
}