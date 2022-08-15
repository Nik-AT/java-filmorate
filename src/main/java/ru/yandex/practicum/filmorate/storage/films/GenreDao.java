package ru.yandex.practicum.filmorate.storage.films;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.GENRES;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GenreDao {
    private final JdbcTemplate jdbcTemplate;

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Genre getGenreById(Integer genreId) {
        String sqlQuery = "select * from genres where genre_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeGenre(rs), genreId);
    }
    public List<Genre> getAllGenre() {
        String sqlQuery = "select * from genres";
        return jdbcTemplate.query(sqlQuery,(rs, rowNum) -> makeGenre(rs));

    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("GENRE_ID"));
        genre.setName(rs.getString("NAME"));
        return genre;
    }
    public void saveGenres(List<Genre> genres, int filmId) {
        String sqlQuery = "merge into GENRE_FILM (film_id, genre_id) key(film_id, genre_id) " +
                "values (?, ?)";
        genres.forEach(s -> jdbcTemplate.update(sqlQuery, filmId, s.getId()));
    }

}
