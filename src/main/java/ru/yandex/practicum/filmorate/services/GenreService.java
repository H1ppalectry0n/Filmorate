package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService {
    public final GenreRepository genreRepository;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Genre findById(Long id) {
        return genreRepository.findById(id);
    }

    public boolean checkGenresExist(Set<Genre> genres) {
        return genreRepository.checkGenresExist(genres);
    }

    public List<Genre> findByFilmId(Long filmId) {
        return genreRepository.findByFilmId(filmId);
    }
}
