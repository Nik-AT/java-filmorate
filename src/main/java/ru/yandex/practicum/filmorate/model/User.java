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
public class User {
    private long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

  //  @JsonIgnore
  //  private final Set<Long> friendsSetId = new HashSet<>();
}
