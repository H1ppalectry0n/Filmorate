package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.mappers.RatingRowMapper;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.util.List;
import java.util.Optional;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({RatingRepository.class, RatingRowMapper.class})
class RatingRepositoryTest {
    private final RatingRepository ratingRepository;

    @Test
    void testFindAllRatings() {
        List<MPARating> ratings = ratingRepository.findAll();

        Assertions.assertFalse(ratings.isEmpty(), "Ratings list should not be empty");
        Assertions.assertEquals(1L, ratings.get(0).getId(), "First rating ID should match");
        Assertions.assertEquals("G", ratings.get(0).getName(), "First rating name should match");
    }

    @Test
    void testFindRatingById() {
        Optional<MPARating> rating = ratingRepository.findById(1L);

        Assertions.assertTrue(rating.isPresent(), "Rating with ID 1 should be present");
        Assertions.assertEquals("G", rating.get().getName(), "Rating name should match");
    }

}