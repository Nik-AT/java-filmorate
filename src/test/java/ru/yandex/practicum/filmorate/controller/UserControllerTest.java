package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.users.UserDb;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void validationIfIdToBeNegative() {
        UserController userController = new UserController(new UserService(new UserDb(new JdbcTemplate())));
        User user = new User(-1, "practicum@ya.ru", "user1", "bob", LocalDate.of(1990, 12, 12));
        assertThrows(NotFoundException.class, () -> userController.validationToUpdateUser(user));
    }

    @Test
    void validationIfEmailNotContainsAt() {
        UserController userController = new UserController(new UserService(new UserDb(new JdbcTemplate())));
        User user = new User(1, "practicumya.ru", "user1", "bob", LocalDate.of(1990, 12, 12));
        assertThrows(ValidationException.class, () -> userController.validation(user));
    }

    @Test
    void validationIfInvalidLogin() {
        UserController userController = new UserController(new UserService(new UserDb(new JdbcTemplate())));
        User user = new User(1, "practicum@ya.ru", "user 1", "bob", LocalDate.of(1990, 12, 12));
        assertThrows(ValidationException.class, () -> userController.validation(user));
    }

    @Test
    void validationIfBirthdayInFuture() {
        UserController userController = new UserController(new UserService(new UserDb(new JdbcTemplate())));
        User user = new User(1, "practicum@ya.ru", "user1", "bob", LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> userController.validation(user));
    }
}