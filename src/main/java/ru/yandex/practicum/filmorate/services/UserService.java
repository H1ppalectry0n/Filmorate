package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final InMemoryUserStorage userStorage;

    public void addFriend(int userId, int friendId) {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(friendId);

        if (user == null || friend == null) {
            throw new UserNotFoundException("Пользователь не существует");
        }

        if (userId == friendId) {
            throw new FilmNotFoundException("нельзя добавить в друзья самого себя");
        }

        // добавление идентификаторов для пользователя который подал заявку
        Set<Integer> userFriends = user.getFriends();
        userFriends.add(friendId);
        user.setFriends(userFriends);

        // добавление идентификаторов для другого пользователя
        Set<Integer> otherFriends = friend.getFriends();
        otherFriends.add(userId);
        friend.setFriends(otherFriends);
    }

    public void removeFriend(int userId, int friendId) {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(friendId);

        if (user == null || friend == null) {
            throw new UserNotFoundException("Пользователь не существует");
        }

        Set<Integer> userFriends = user.getFriends();
        userFriends.remove(friendId);

        Set<Integer> otherFriends = friend.getFriends();
        otherFriends.remove(userId);
    }

    public List<User> getCommonFriends(int userId, int otherUserId) {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(otherUserId);

        if (user == null || friend == null) {
            throw new UserNotFoundException("Пользователь не существует");
        }

        return user.getFriends().stream()
                .filter(friend.getFriends()::contains)
                .map(userStorage::getUserById)
                .toList();
    }

    public List<User> getUserFriends(int userId) {
        final User user = userStorage.getUserById(userId);

        if (user == null) {
            throw new FilmNotFoundException("Пользователь не существует");
        }

        return user.getFriends().stream()
                .map(userStorage::getUserById)
                .toList();
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User updatedUser) {
        return userStorage.updateUser(updatedUser);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }
}
