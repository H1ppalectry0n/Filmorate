package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void addFriend(Long userId, Long friendId) {
        if (userRepository.findById(userId).isEmpty() ||
                userRepository.findById(friendId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не существует");
        }

        userRepository.addFriend(userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        if (userRepository.findById(userId).isEmpty() ||
                userRepository.findById(friendId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не существует");
        }

        userRepository.removeFriend(userId, friendId);
    }

    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        if (userRepository.findById(userId).isEmpty() ||
                userRepository.findById(otherUserId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не существует");
        }

        return userRepository.getCommonFriends(userId, otherUserId);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + " не найден"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User updatedUser) {
        if (userRepository.findById(updatedUser.getId()).isEmpty()) {
            throw new UserNotFoundException("Пользователь не существует");
        }

        return userRepository.update(updatedUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUserFriends(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("Пользователь не существует");
        }

        return userRepository.findFriends(id);
    }
}
