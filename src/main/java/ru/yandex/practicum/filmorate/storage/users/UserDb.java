package ru.yandex.practicum.filmorate.storage.users;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Primary
@Component
public class UserDb implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        String sqlQuery = "INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        sqlQuery = "SELECT USER_ID FROM USERS ORDER BY USER_ID DESC LIMIT 1";
        Long userId = jdbcTemplate.queryForObject(sqlQuery, Long.class);
        user.setId(userId);
        return getUserById(userId);
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "UPDATE USERS SET EMAIL=?, LOGIN =?, NAME=?, BIRTHDAY=? WHERE USER_ID=?";
        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return getUserById(user.getId());
    }

    @Override
    public List<User> getUsers() {
        String sqlQuery = "SELECT * FROM USERS";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public User getUserById(long userid) {
        String sqlQuery = "SELECT * FROM USERS WHERE USER_ID =?";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeUser(rs), userid);
    }

    @Override
    public void addFriend(User user, User friend) {
        String sqlQuery = "INSERT INTO FRIENDSHIP (USER_INVITE, USER_TAKE) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, user.getId(), friend.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        String sqlQuery = "DELETE FROM FRIENDSHIP " +
                "WHERE USER_INVITE = ? AND USER_TAKE = ?";
        jdbcTemplate.update(sqlQuery, user.getId(), friend.getId());
    }

    @Override
    public List<Long> getUsersByFriend(long userId) {
        String sqlFriendsByUserId = "SELECT USER_TAKE FROM FRIENDSHIP WHERE USER_INVITE = ?";
        return jdbcTemplate.query(
                sqlFriendsByUserId, (rs, rowNum) -> rs.getLong("USER_TAKE"), userId);
    }

    private User makeUser(ResultSet rs) throws SQLException {
        User makeUser = new User();
        makeUser.setId(rs.getInt("USER_ID"));
        makeUser.setEmail(rs.getString("EMAIL"));
        makeUser.setLogin(rs.getString("LOGIN"));
        makeUser.setName(rs.getString("NAME"));
        makeUser.setBirthday(LocalDate.parse(rs.getString("BIRTHDAY"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return makeUser;
    }
}
