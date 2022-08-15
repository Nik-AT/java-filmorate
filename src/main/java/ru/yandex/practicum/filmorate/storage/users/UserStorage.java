package ru.yandex.practicum.filmorate.storage.users;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    User getUserById(long userid);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    List<Long> getUsersByFriend(long id);
}
