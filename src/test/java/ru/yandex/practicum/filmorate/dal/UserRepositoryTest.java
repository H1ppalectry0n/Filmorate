package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserRepository.class, UserRowMapper.class
})
class UserRepositoryTest {
    private final UserRepository userRepository;

    @Test
    void testFindAllUsers() {
        List<User> users = userRepository.findAll();

        Assertions.assertEquals(4, users.size(), "Should return 4 users");
        Assertions.assertEquals("user1@example.com", users.get(0).getEmail(), "First user's email should match");
    }

    @Test
    void testFindUserById() {
        Optional<User> user = userRepository.findById(1L);

        Assertions.assertTrue(user.isPresent(), "User with ID 1 should be present");
        Assertions.assertEquals(1L, user.get().getId(), "User ID should be 1");
    }

    @Test
    void testAddAndRemoveFriend() {
        userRepository.addFriend(2L, 1L);

        List<User> friends = userRepository.findFriends(2L);
        Assertions.assertFalse(friends.isEmpty(), "Friends list should not be empty");
        Assertions.assertEquals(1L, friends.get(0).getId(), "Friend ID should be 2");

        userRepository.removeFriend(2L, 1L);

        friends = userRepository.findFriends(2L);
        Assertions.assertTrue(friends.isEmpty(), "Friends list should be empty after removal");
    }

}