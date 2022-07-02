package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FilmController {
    private final Map<Integer, Film> filmMap = new HashMap();
    static int idFilms;

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("films")
    ArrayList<Film> getFilm() {
        return new ArrayList<>(filmMap.values());
    }

    @PostMapping("films")
    public Film createFilm(@RequestBody Film film) {
        ++idFilms;
        film.setId(idFilms);
        validation(film);
        filmMap.put(idFilms, film);
        log.info("Создание фильма: {}", film);
        return film;
    }

    @PutMapping("films")
    public Film updateFilm(@RequestBody Film film) {
        int id = film.getId();
        validation(film);
        filmMap.put(id, film);
        log.info("Обновление фильма: {}", film);
        return film;
    }

    void validation(Film film) {
        if (film.getName().isEmpty()) {
            log.info("Не верно указано название");
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() >= 200) {
            log.info("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.info("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        if (film.getId() <= 0) {
            log.info("Не верный id");
            throw new ValidationException("Не верный id");
        }
    }
}
