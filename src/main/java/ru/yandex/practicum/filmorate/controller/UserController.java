package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        validation(user);
        log.info("Создание пользователя: {}", user);
        return userService.createUser(user);
    }
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        validationToUpdateUser(user);
        log.info("Обновление пользователя: {}", user);
        return userService.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> getUser() {
        log.info("Все пользователи: {}", userService.getUser());
        return userService.getUser();
    }

    protected void validation(User user) {
        if (user.getEmail().isEmpty() || !(user.getEmail().contains("@"))) {
            log.warn("Не верный формат email = {}", user);
            throw new ValidationException("Не верный email");
        }
        if (user.getLogin().contains(" ") || user.getLogin().isEmpty()) {
            log.warn("Логин пустой или содержит пробел = {}", user);
            throw new ValidationException("Не верный логин");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("День Рождение не может быть в будущем = {}", user);
            throw new ValidationException("День Рождение не может быть в будущем");
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
    private void validationToUpdateUser(User user) {
        if (!(userService.getUserMap().containsKey(user.getId()))) {
            log.warn("Не верный id = {}", user);
            throw new ValidationException("Не верный id");
        }
    }
}
