package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Genre {
    private Integer id;
    private String name;

    @JsonCreator
    public Genre(Integer id) {
        this.id = id;
    }

//    public Genre(GENRES name) {
//        this.name = name;
//    }
}
