package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void validationIfIdToBeNegative() {
        UserController userController = new UserController();
        User user = new User(-1, "practicum@ya.ru", "user1", "bob", LocalDate.of(1990, 12, 12));
        assertThrows(ValidationException.class, () -> userController.validation(user));
    }

    @Test
    void validationIfEmailNotContainsAt() {
        UserController userController = new UserController();
        User user = new User(1, "practicumya.ru", "user1", "bob", LocalDate.of(1990, 12, 12));
        assertThrows(ValidationException.class, () -> userController.validation(user));
    }

    @Test
    void validationIfInvalidLogin() {
        UserController userController = new UserController();
        User user = new User(1, "practicum@ya.ru", "user 1", "bob", LocalDate.of(1990, 12, 12));
        assertThrows(ValidationException.class, () -> userController.validation(user));
    }

    @Test
    void validationIfBirthdayInFuture() {
        UserController userController = new UserController();
        User user = new User(1, "practicum@ya.ru", "user1", "bob", LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> userController.validation(user));
    }
}