package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private Integer id;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Login cannot be empty")
    @Pattern(regexp = "^\\S*$", message = "Login cannot contain spaces")
    private String login;

    private String name;

    @NotNull(message = "Birthday is required")
    @Past(message = "Birthday cannot be in the future")
    private LocalDate birthday;

    private Set<Integer> friends;
}
