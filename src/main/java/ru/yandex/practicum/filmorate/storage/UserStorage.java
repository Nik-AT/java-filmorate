package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    ArrayList<User> getUsers();

    Map<Long, User> getUserMap();

    User getUserById(long userid);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    List<User> getUsersByFriend(User user);

    List<User> getFriendCommonByOther(long id, long otherId);
}
