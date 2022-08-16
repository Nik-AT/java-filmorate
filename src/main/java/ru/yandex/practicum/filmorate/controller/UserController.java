package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class UserController {

    final UserService userService;

    @Autowired
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
    public List<User> getUsers() {
        log.info("Все пользователи: {}", userService.getUser());
        return userService.getUser();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        validationToUserById(id);
        log.info("Пользователь с Ид: {}", id);
        return userService.getUserById(id);
    }

    @PutMapping("/users/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable long userId, @PathVariable long friendId) {
        validationFriends(userId, friendId);
        log.info(String.format("Пользователь с ИД %d добавил в друзья пользователя с ИД %d", userId, friendId));
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/users/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable long userId, @PathVariable long friendId) {
        validationFriends(userId, friendId);
        log.info(String.format("Пользователь с ИД %d удалил из друзей пользователя с ИД %d", userId, friendId));
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getUsersByFriend(@PathVariable long id) {
        validationToUserById(id);
        log.info("Все друзья: {}", userService.getUsersByFriend(id));
        return userService.getUsersByFriend(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getFriendCommonByOther(@PathVariable long id, @PathVariable long otherId) {
        validationFriends(id, otherId);
        log.info("Общие друзья: {}", userService.getUsersByFriend(id));
        return userService.getFriendCommonByOther(id, otherId);
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

    protected void validationToUpdateUser(User user) {
        if (user.getId() < 0) {
            log.warn("Не верный id = {}", user);
            throw new NotFoundException("Не верный id");
        }
    }

    protected void validationToUserById(long id) {
        if (id < 0) {
            log.warn("Не верный id = {}", id);
            throw new NotFoundException("Не корректно введен id " + id);
        }
    }

    protected void validationFriends(long userId, long friendId) {
        if (userId < 0) {
            log.warn("Не верный id у user = {}", userId);
            throw new NotFoundException("Не корректно введен id " + userId);
        }
        if (friendId < 0) {
            log.warn("Не верный id у friendId = {}", friendId);
            throw new NotFoundException("Не корректно введен id у friendId " + friendId);
        }
    }
}
