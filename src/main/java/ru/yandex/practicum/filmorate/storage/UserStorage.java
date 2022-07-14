package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Map;

public interface UserStorage {
    User createUser(User user);
    User updateUser(User user);
    ArrayList<User> getUser();
    Map<Long, User> getUserMap();
}
