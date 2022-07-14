package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Long, User> userMap = new HashMap<>();
    private static long idUsers;

    @Override
    public User createUser(User user) {
        ++idUsers;
        user.setId(idUsers);
        userMap.put(idUsers, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        long id = user.getId();
        userMap.put(id, user);
        return user;
    }

    @Override
    public ArrayList<User> getUser() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public Map<Long, User> getUserMap() {
        return userMap;
    }
}
