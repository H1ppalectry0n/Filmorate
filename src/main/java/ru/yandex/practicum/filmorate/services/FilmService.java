package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.GenreValidationException;
import ru.yandex.practicum.filmorate.exceptions.RatingValidationException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final RatingService ratingService;
    private final GenreService genreService;

    public void addLike(Long filmId, Long userId) {
        if (filmRepository.findById(filmId).isEmpty()) {
            throw new FilmNotFoundException("Фильм не существует");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не существует");
        }
        filmRepository.addFavorite(filmId, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        if (filmRepository.findById(filmId).isEmpty()) {
            throw new FilmNotFoundException("Фильм не существует");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не существует");
        }
        filmRepository.removeFavorite(filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmRepository.findAll().stream()
                .peek(film -> film.setFavorites(userRepository.getUserFavoriteFilmByFilmId(film.getId())))
                .sorted((film, other) -> Integer.compare(other.getFavorites().size(), film.getFavorites().size()))
                .limit(count)
                .toList();
    }

    public Film addFilm(Film film) {
        if (!genreService.checkGenresExist(new HashSet<>(film.getGenres()))) {
            throw new GenreValidationException("Genres fail");
        }

        if (!ratingService.checkRating(film.getMpa().getId())) {
            throw new RatingValidationException("Rating fail");
        }

        return filmRepository.save(film);
    }

    public Film updateFilm(Film updatedFilm) {
        if (filmRepository.findById(updatedFilm.getId()).isEmpty()) {
            throw new FilmNotFoundException("Фильм не существует");
        }
        return filmRepository.update(updatedFilm);
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmById(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException("Фильм не существует"));
        film.setGenres(genreService.findByFilmId(film.getId()));
        return film;
    }
}