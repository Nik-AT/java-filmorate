package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//class FilmControllerTest {
//
//    @Test
//    void validationToBeNameIsEmpty() {
//        FilmController filmController = new FilmController(inMemoryFilmStorage);
//        Film film = new Film (1,"", "description1", LocalDate
//                .of(1990,10,10),100);
//        assertThrows(ValidationException.class, () -> filmController.validation(film));
//    }
//
//    @Test
//    void validationIfDescriptionMoreThen200Character() {
//        FilmController filmController = new FilmController(inMemoryFilmStorage);
//        Film film = new Film (1,"film1", "Suspected graceful diverted feel humanity education " +
//                "chapter moment basket done sure basket felt could bed. Dull jointure life stairs oppose sociable " +
//                "dependent park least assured honoured settle besides",
//                LocalDate.of(1990,10,10),100);
//        assertThrows(ValidationException.class, () -> filmController.validation(film));
//    }
//
//    @Test
//    void validationIfLocalDateIsBefore1895_12_28() {
//        FilmController filmController = new FilmController(inMemoryFilmStorage);
//        Film film = new Film (1,"film1", "description1", LocalDate
//                .of(1895,12,27),100);
//        assertThrows(ValidationException.class, () -> filmController.validation(film));
//    }
//
//    @Test
//    void validationIfDurationIsNegative() {
//        FilmController filmController = new FilmController(inMemoryFilmStorage);
//        Film film = new Film (1,"film1", "description1", LocalDate
//                .of(1990,10,10),-1);
//        assertThrows(ValidationException.class, () -> filmController.validation(film));
//    }
//
//    @Test
//    void validationIfIdIsNegative() {
//        FilmController filmController = new FilmController(inMemoryFilmStorage);
//        Film film = new Film (-1,"film1", "description1", LocalDate
//                .of(1990,10,10),100);
//        assertThrows(ValidationException.class, () -> filmController.validation(film));
//    }
//}