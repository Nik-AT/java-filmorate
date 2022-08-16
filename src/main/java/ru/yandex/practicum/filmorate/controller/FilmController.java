package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/films")
    public List<Film> getFilm() {
        log.info("Список фильмов: {}", filmService.getFilm());
        return filmService.getFilm();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable long id) {
        validationGetByFilmId(id);
        log.info("Фильм с ИД: {}", filmService.getFilm());
        return filmService.getFilmById(id);
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) {
        validation(film);
        log.info("Создание фильма: {}", film);
        return filmService.createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        validationToUpdateFilm(film);
        log.info("Обновление фильма: {}", film);
        return filmService.updateFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable long id, @PathVariable long userId) {
        log.info(String.format("Фильм с ИД %d лайкнул пользователя с ИД %d", id, userId));
        filmService.addLikeToFilm(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        validationDelete(id, userId);
        log.info(String.format("Фильм с ИД %d лайкнул пользователя с ИД %d", id, userId));
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> popularFilm(@RequestParam(defaultValue = "10", required = false) Integer count) {
        log.info("Фильм по популярности: {}", filmService.popularFilm(count));
        return filmService.popularFilm(count);
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
    }

    protected void validationToUpdateFilm(Film film) {
        if (film.getId() < 0) {
            log.warn("Не верный id = {}", film);
            throw new NotFoundException("Не корректно введен id " + film.getId());
        }
    }

    protected void validationGetByFilmId(long id) {
        if (id < 0) {
            log.warn("Не верный id = {}", id);
            throw new NotFoundException("Не верный id");
        }
    }

    private void validationDelete(long id, long userId) {
        if (id < 0) {
            log.warn("Не верный id фильма = {}", id);
            throw new NotFoundException("Не верный id");
        }
        if (userId < 0) {
            log.warn("Не верный id user = {}", userId);
            throw new NotFoundException("Не верный id");
        }
    }
}
