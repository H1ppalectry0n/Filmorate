package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validators.ReleaseDateConstraint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Film.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;

    @NotNull(message = "Release date is required")
    @PastOrPresent(message = "Release date cannot be in the future")
    @ReleaseDateConstraint(message = "Release date cannot be earlier than December 28, 1895")
    private LocalDate releaseDate;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    private Integer duration;

    @NotNull(message = "MPA is required")
    private MPARating mpa;

    private List<Genre> genres = new ArrayList<>();

    private Set<Long> favorites = new HashSet<>();

}
