package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FilmController {
    @Autowired
    private final FilmService filmService;

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> getFilm() {
        return filmService.getFilm();
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) {
        validation(film);
        log.info("Создание фильма: {}", film);
        return filmService.createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        validation(film);
        log.info("Обновление фильма: {}", film);
        return filmService.updateFilm(film);
    }

    protected void validation(Film film) {
        if (film.getName().isEmpty()) {
            log.warn("Название фильма не может быть пустым = {}", film);
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() >= 200) {
            log.warn("Описание более 200 символов = {}", film);
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза раньше 28 декабря 1895 года = {}", film);
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.warn("Продолжительность фильма отрицательная = {}", film);
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        if (film.getId() < 0) {
            log.warn("Не корректный id = {}", film);
            throw new ValidationException("Не верный id");
        }
    }
}
