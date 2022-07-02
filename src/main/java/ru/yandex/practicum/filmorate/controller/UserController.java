package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private Map<Long, User> userMap = new HashMap<>();
    private static long idUsers;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
        if (user.getId() <= 0) {
            log.warn("Не верный id = {}", user);
            throw new ValidationException("Не верный id");
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        ++idUsers;
        user.setId(idUsers);
        validation(user);
        userMap.put(idUsers, user);
        log.info("Создание пользователя: {}", user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        long id = user.getId();
        validation(user);
        userMap.put(id, user);
        log.info("Обновление пользователя: {}", user);
        return user;
    }

    @GetMapping("/users")
    ArrayList<User> getUser() {
        return new ArrayList<>(userMap.values());
    }
}
