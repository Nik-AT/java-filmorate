package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private final UserStorage userStorage;


    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public List<User> getUser() {
        return userStorage.getUsers();
    }

    public Map<Long, User> getUserMap() {
        return userStorage.getUserMap();
    }

    public User getUserById(long userId) {
        User userById = userStorage.getUserById(userId);
        if (userById == null) {
            throw new NotFoundException("Пользователь с ИД: " + userId + " не найден");
        }
        return userStorage.getUserById(userId);
    }

    public void addFriend(long userid, long friendId) {
        User userById = userStorage.getUserById(userid);
        User friend = userStorage.getUserById(friendId);
        if (userid < 0 || userById == null) {
            throw new NotFoundException("Не найден пользователь с ИД: " + userid);
        }
        if (friendId < 0 || friend == null) {
            throw new NotFoundException("Не найден друг с ИД: " + friendId);
        }
        userStorage.addFriend(userById, friend);
    }

    public void deleteFriend(long userid, long friendId) {
        User userById = userStorage.getUserById(userid);
        User friend = userStorage.getUserById(friendId);
        if (userid < 0 || userById == null) {
            throw new NotFoundException("Не найден пользователь с ИД: " + userid);
        }
        if (friendId < 0 || friend == null) {
            throw new NotFoundException("Не найден друг с ИД: " + friendId);
        }
        userStorage.deleteFriend(userById, friend);
    }

    public List<User> getUsersByFriend(long id) {
        User userById = userStorage.getUserById(id);
        return userStorage.getUsersByFriend(userById);
    }

    public List<User> getFriendCommonByOther(long id, long otherId) {
        return userStorage.getFriendCommonByOther(id, otherId);
    }
}
