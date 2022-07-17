package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Film {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    @JsonIgnore
    private Set<Long> userIdLike = new HashSet<>();
}
