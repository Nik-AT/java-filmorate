package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Rating {
    private Integer id;
    @JsonProperty("name")
    private String ratingMpa;

    @JsonCreator
    public Rating(Integer id) {
        this.id = id;
    }

    public Rating(Integer id, String ratingMpa) {
        this.id = id;
        this.ratingMpa = ratingMpa;
    }
}
