package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.RatingRepository;
import ru.yandex.practicum.filmorate.exceptions.RatingExeption;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    public final RatingRepository ratingRepository;

    public List<MPARating> findAll() {
        return ratingRepository.findAll();
    }

    public MPARating findById(Long id) {
        return ratingRepository.findById(id).orElseThrow(() -> new RatingExeption("Рейинга с id=" + id + " не существует"));
    }

    public boolean checkRating(Long id) {
        return ratingRepository.findById(id).isPresent();
    }

}
