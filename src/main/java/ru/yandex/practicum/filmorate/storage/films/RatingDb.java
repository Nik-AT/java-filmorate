package ru.yandex.practicum.filmorate.storage.films;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class RatingDb {
    private final JdbcTemplate jdbcTemplate;

    public RatingDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Rating getRatingById(Integer ratingId) {
        String sqlQuery = "SELECT * FROM RATING_MPA WHERE RATING_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeRating(rs), ratingId);
    }

    public List<Rating> getAllRating() {
        String sqlQuery = "SELECT * FROM RATING_MPA";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeRating(rs));

    }

    private Rating makeRating(ResultSet rs) throws SQLException {
        return new Rating(rs.getInt("RATING_ID"),
                rs.getString("RATING_MPA_NAME"));
    }
}
