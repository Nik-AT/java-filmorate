package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

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
    public ArrayList<User> getUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public Map<Long, User> getUserMap() {
        return userMap;
    }

    @Override
    public User getUserById(long userid) {
        return userMap.get(userid);
    }

    @Override
    public void addFriend(User user, User friend) {
        user.getFriendsSetId().add(friend.getId());
        friend.getFriendsSetId().add(user.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        user.getFriendsSetId().remove(friend.getId());
        friend.getFriendsSetId().remove(user.getId());
    }

    @Override
    public List<User> getUsersByFriend(User user) {
        Set<Long> friendsSetId = user.getFriendsSetId();
        List<User> friends = new ArrayList<>();
        for (Long idSet : friendsSetId) {
            friends.add(getUserById(idSet));
        }
        return friends;
    }

    @Override
    public List<User> getFriendCommonByOther(long userid, long otherId) {
        Set<Long> userById = getUserById(userid).getFriendsSetId();
        Set<Long> otherUser = getUserById(otherId).getFriendsSetId();
        List<User> commonFriends = new ArrayList<>();
        for (Long id : userById) {
            if (otherUser.contains(id)) {
                commonFriends.add(getUserById(id));
            }
        }
        return commonFriends;
    }


}
