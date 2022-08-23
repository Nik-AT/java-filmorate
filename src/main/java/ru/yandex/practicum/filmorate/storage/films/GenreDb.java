package ru.yandex.practicum.filmorate.storage.films;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GenreDb implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getGenreById(Integer genreId) {
        String sqlQuery = "SELECT * FROM GENRES where GENRE_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeGenre(rs), genreId);
    }

    @Override
    public List<Genre> getAllGenre() {
        String sqlQuery = "SELECT * FROM GENRES";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeGenre(rs));

    }

    protected Genre makeGenre(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("GENRE_ID");
        String name = rs.getString("NAME");
        Genre makeGenre = new Genre(id);
        makeGenre.setName(name);
        return makeGenre;
    }
}
