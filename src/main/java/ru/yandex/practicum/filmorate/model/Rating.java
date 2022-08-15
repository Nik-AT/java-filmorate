package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Rating {
    private Integer id;
    @JsonProperty("name")
    private String ratingMpa;

    public Rating(Integer id) {
        this.id = id;
    }
}
