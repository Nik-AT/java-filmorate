package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Genre {
    private Integer id;
    private GENRES name;

    @JsonCreator
    public Genre(Integer id) {
        this.id = id;
    }
}
