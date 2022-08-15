package ru.yandex.practicum.filmorate.storage.films;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.GENRES;
import ru.yandex.practicum.filmorate.model.Genre;
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
        String sqlQuery = "select * from RATING_MPA where RATING_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeRating(rs), ratingId);
    }
    public List<Rating> getAllRating() {
        String sqlQuery = "select * from RATING_MPA";
        return jdbcTemplate.query(sqlQuery,(rs, rowNum) -> makeRating(rs));

    }

    private Rating makeRating(ResultSet rs) throws SQLException {
        Rating rating = new Rating();
        rating.setId(rs.getInt("RATING_ID"));
        rating.setRatingMpa(rs.getString("RATING_MPA_NAME"));
        return rating;
    }
}
