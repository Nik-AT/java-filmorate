package ru.yandex.practicum.filmorate.storage.users;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage  {

    private Map<Long, User> userMap = new HashMap<>();
    private static long idUsers;


    public User createUser(User user) {
        ++idUsers;
        user.setId(idUsers);
        userMap.put(idUsers, user);
        return null;
    }


    public User updateUser(User user) {
        long id = user.getId();
        userMap.put(id, user);
        return null;
    }


    public ArrayList<User> getUsers() {
        return null;
    }


    public Map<Long, User> getUserMap() {
        return userMap;
    }


    public User getUserById(long userid) {
        return null;
    }


//    public void addFriend(User user, User friend) {
//        user.getFriendsSetId().add(friend.getId());
//        friend.getFriendsSetId().add(user.getId());
//    }
//
//
//    public void deleteFriend(User user, User friend) {
//        user.getFriendsSetId().remove(friend.getId());
//        friend.getFriendsSetId().remove(user.getId());
//    }
//
//
//    public List<User> getUsersByFriend(User user) {
//        Set<Long> friendsSetId = user.getFriendsSetId();
//        List<User> friends = new ArrayList<>();
//        for (Long idSet : friendsSetId) {
//            friends.add(getUserById(idSet));
//        }
//        return null;
//    }


//    public List<User> getFriendCommonByOther(long userid, long otherId) {
//        Set<Long> userById = getUserById(userid).getFriendsSetId();
//        Set<Long> otherUser = getUserById(otherId).getFriendsSetId();
//        List<User> commonFriends = new ArrayList<>();
//        for (Long id : userById) {
//            if (otherUser.contains(id)) {
//                commonFriends.add(getUserById(id));
//            }
//        }
//        return null;
//    }


}
