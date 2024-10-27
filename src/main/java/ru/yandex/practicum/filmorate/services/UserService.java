package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmOrUserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final InMemoryUserStorage userStorage;
    private final Map<Integer, Set<Integer>> friendsList = new HashMap<>();

    public void addFriend(int userId, int friendId) {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(friendId);

        if (user == null || friend == null) {
            throw new FilmOrUserNotFoundException("Пользователь не существует");
        }

        if (userId == friendId) {
            throw new FilmOrUserNotFoundException("нельзя добавить в друзья самого себя");
        }

        // добавление идентификаторов для пользователя который подал заявку
        Set<Integer> userFriends = friendsList.get(userId);
        if (userFriends == null) {
            userFriends = new HashSet<>();
        }
        userFriends.add(friendId);
        friendsList.put(userId, userFriends);

        // добавление идентификаторов для другого пользователя
        Set<Integer> otherFriends = friendsList.get(friendId);
        if (otherFriends == null) {
            otherFriends = new HashSet<>();
        }
        otherFriends.add(userId);
        friendsList.put(friendId, otherFriends);
    }

    public void removeFriend(int userId, int friendId) {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(friendId);

        if (user == null || friend == null) {
            throw new FilmOrUserNotFoundException("Пользователь не существует");
        }

        Set<Integer> userFriends = friendsList.get(userId);
        if (userFriends != null) {
            userFriends.remove(friendId);
        }

        Set<Integer> otherFriends = friendsList.get(friendId);
        if (otherFriends != null) {
            otherFriends.remove(userId);
        }
    }

    public void removeAllFriends(int userId) {
        // удаление пользователя из списка друзей
        friendsList.remove(userId);

        // удаление у других пользователей
        for (Set<Integer> other : friendsList.values()) {
            other.remove(userId);
        }
    }

    public List<User> getCommonFriends(int userId, int otherUserId) {
        if (!friendsList.containsKey(userId) || !friendsList.containsKey(otherUserId)) {
            return new ArrayList<>();
        }

        return friendsList.get(userId).stream()
                .filter(friendsList.get(otherUserId)::contains)
                .map(userStorage::getUserById)
                .toList();
    }

    public List<User> getUserFriends(int userId) {
        final User user = userStorage.getUserById(userId);

        if (user == null) {
            throw new FilmOrUserNotFoundException("Пользователь не существует");
        }

        if (!friendsList.containsKey(userId)) {
            return new ArrayList<>();
        }

        return friendsList.get(userId).stream()
                .map(userStorage::getUserById)
                .toList();
    }

}
