package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int filmIdCounter = 1;

    @Override
    public Film addFilm(Film film) {
        film.setId(filmIdCounter++);
        film.setFavorites(new HashSet<>());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (film.getId() == null || !films.containsKey(film.getId())) {
            return null;
        }
        film.setFavorites(films.get(film.getId()).getFavorites());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public boolean deleteFilm(int id) {
        return films.remove(id) != null;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(int id) {
        return films.get(id);
    }
}
