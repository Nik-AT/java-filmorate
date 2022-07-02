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
    private Map<Integer, User> userMap = new HashMap<>();
    static int idUsers;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    void validation(User user) {
        if (user.getEmail().isEmpty() || !(user.getEmail().contains("@"))) {
            log.info("Не верный email");
            throw new ValidationException("Не верный email");
        }
        if (user.getLogin().contains(" ") || user.getLogin().isEmpty()) {
            log.info("Не верный логин");
            throw new ValidationException("Не верный логин");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("День Рождение не может быть в будущем");
            throw new ValidationException("День Рождение не может быть в будущем");
        }
        if (user.getId() <= 0) {
            log.info("Не верный id");
            throw new ValidationException("Не верный id");
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    @PostMapping("users")
    public User createUser(@RequestBody User user) {
        ++idUsers;
        user.setId(idUsers);
        validation(user);
        userMap.put(idUsers, user);
        log.info("Создание пользователя: {}", user);
        return user;
    }

    @PutMapping("users")
    public User updateUser(@RequestBody User user) {
        int id = user.getId();
        validation(user);
        userMap.put(id, user);
        log.info("Обновление пользователя: {}", user);
        return user;
    }

    @GetMapping("users")
    ArrayList<User> getUser() {
        return new ArrayList<>(userMap.values());
    }
}
