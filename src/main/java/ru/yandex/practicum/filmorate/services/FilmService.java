package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage filmStorage;

    // первое id фильма
    // второе id пользователей
    private final Map<Integer, Set<Integer>> favoritesFilms = new HashMap<>();

    public void addLike(int filmId, int userId) {
        Set<Integer> likes = favoritesFilms.get(filmId);
        if (likes == null) {
            likes = new HashSet<>();
        }

        likes.add(userId);
        favoritesFilms.put(filmId, likes);
    }

    public void removeLike(int filmId, int userId) {
        Set<Integer> likes = favoritesFilms.get(filmId);
        if (likes != null) {
            likes.remove(userId);
        }
    }

    // При удалении пользователя
    public void removeAllUserLikes(int userId) {
        for (Set<Integer> likes : favoritesFilms.values()) {
            likes.remove(userId);
        }
    }

    // при удалении фильма пока не
    public void removeAllLikes(int filmId) {
        favoritesFilms.remove(filmId);
    }

    public List<Film> getPopularFilms(int count) {
        return favoritesFilms.keySet().stream()
                .sorted((id, otherId) -> Integer.compare(favoritesFilms.get(otherId).size(), favoritesFilms.get(id).size()))
                .limit(count)
                .map(filmStorage::getFilmById)
                .toList();
    }

}
