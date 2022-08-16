package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.films.FilmDb;
import ru.yandex.practicum.filmorate.storage.films.GenreDb;
import ru.yandex.practicum.filmorate.storage.films.RatingDb;
import ru.yandex.practicum.filmorate.storage.users.UserDb;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {

    @Test
    void validationToBeNameIsEmpty() {
        FilmController filmController = new FilmController(new FilmService
                (new FilmDb(new JdbcTemplate(),
                        new GenreDb(new JdbcTemplate()),
                        new RatingDb(new JdbcTemplate())),
                        new UserDb(new JdbcTemplate())));
        Film film = new Film(1, "", "description1", LocalDate
                .of(1990, 10, 10), 100, new Rating());
        assertThrows(ValidationException.class, () -> filmController.validation(film));
    }

    @Test
    void validationIfDescriptionMoreThen200Character() {
        FilmController filmController = new FilmController(new FilmService
                (new FilmDb(new JdbcTemplate(),
                        new GenreDb(new JdbcTemplate()),
                        new RatingDb(new JdbcTemplate())),
                        new UserDb(new JdbcTemplate())));
        Film film = new Film(1, "film1", "Suspected graceful diverted feel humanity education " +
                "chapter moment basket done sure basket felt could bed. Dull jointure life stairs oppose sociable " +
                "dependent park least assured honoured settle besides",
                LocalDate.of(1990, 10, 10), 100, new Rating());
        assertThrows(ValidationException.class, () -> filmController.validation(film));
    }

    @Test
    void validationIfLocalDateIsBefore1895_12_28() {
        FilmController filmController = new FilmController(new FilmService
                (new FilmDb(new JdbcTemplate(),
                        new GenreDb(new JdbcTemplate()),
                        new RatingDb(new JdbcTemplate())),
                        new UserDb(new JdbcTemplate())));
        Film film = new Film(1, "film1", "description1", LocalDate
                .of(1895, 12, 27), 100, new Rating());
        assertThrows(ValidationException.class, () -> filmController.validation(film));
    }

    //
    @Test
    void validationIfDurationIsNegative() {
        FilmController filmController = new FilmController(new FilmService
                (new FilmDb(new JdbcTemplate(),
                        new GenreDb(new JdbcTemplate()),
                        new RatingDb(new JdbcTemplate())),
                        new UserDb(new JdbcTemplate())));
        Film film = new Film(1, "film1", "description1", LocalDate
                .of(1990, 10, 10), -1, new Rating());
        assertThrows(ValidationException.class, () -> filmController.validation(film));
    }

    @Test
    void validationIfIdIsNegative() {
        FilmController filmController = new FilmController(new FilmService
                (new FilmDb(new JdbcTemplate(),
                        new GenreDb(new JdbcTemplate()),
                        new RatingDb(new JdbcTemplate())),
                        new UserDb(new JdbcTemplate())));
        Film film = new Film(-1, "film1", "description1", LocalDate
                .of(1990, 10, 10), 100, new Rating());
        assertThrows(NotFoundException.class, () -> filmController.validationToUpdateFilm(film));
    }
}

