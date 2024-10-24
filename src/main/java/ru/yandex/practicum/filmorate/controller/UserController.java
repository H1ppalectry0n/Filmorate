package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Map<Integer, User> users = new HashMap<>();
    private int userIdCounter = 1;

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Validation failed for adding user: {}", result.getFieldErrors());
            throw new ValidationException(collectValidationErrors(result));
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(userIdCounter++);
        users.put(user.getId(), user);
        logger.info("User added successfully: {}", user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User updatedUser, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Validation failed for updating user: {}", result.getFieldErrors());
            throw new ValidationException(collectValidationErrors(result));
        }

        if (updatedUser.getId() == null || !users.containsKey(updatedUser.getId())) {
            logger.warn("User not found with ID: {}", updatedUser.getId());
            return new ResponseEntity<User>(updatedUser, HttpStatus.NOT_FOUND);
        }
        if (updatedUser.getName() == null || updatedUser.getName().isEmpty()) {
            updatedUser.setName(updatedUser.getLogin());
        }
        users.put(updatedUser.getId(), updatedUser);
        logger.info("User updated successfully: {}", users.get(updatedUser.getId()));
        return ResponseEntity.ok(updatedUser);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Retrieving all users");
        return ResponseEntity.ok(new ArrayList<>(users.values()));
    }

    // Helper method to collect validation errors
    private Map<String, String> collectValidationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
