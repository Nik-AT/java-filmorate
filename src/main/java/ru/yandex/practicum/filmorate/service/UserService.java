package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.users.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
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
        List<Long> friends = userStorage.getUsersByFriend(userById.getId());
        return userStorage.getUsers()
                .stream()
                .filter(user -> friends.contains(user.getId()))
                .collect(Collectors.toList());
    }

    public List<User> getFriendCommonByOther(long id, long otherId) {
        User userById = userStorage.getUserById(id);
        User otherUserById = userStorage.getUserById(otherId);
        List<Long> userFriends = userStorage.getUsersByFriend(userById.getId());
        List<Long> otherUserFriends = userStorage.getUsersByFriend(otherUserById.getId());
        List<Long> commonFriends = userFriends
                .stream()
                .filter(otherUserFriends::contains)
                .collect(Collectors.toList());
        List<User> userList = userStorage.getUsers();
        return userList.stream()
                .filter(user -> commonFriends.contains(user.getId()))
                .collect(Collectors.toList());
    }
}
