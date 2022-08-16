package ru.yandex.practicum.filmorate.storage.films;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.GENRES;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreDb {
    private final JdbcTemplate jdbcTemplate;

    public GenreDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Genre getGenreById(Integer genreId) {
        String sqlQuery = "SELECT * FROM GENRES where GENRE_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeGenre(rs), genreId);
    }

    public List<Genre> getAllGenre() {
        String sqlQuery = "SELECT * FROM GENRES";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeGenre(rs));

    }

    protected Genre makeGenre(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("GENRE_ID");
        String name = rs.getString("NAME");
        Genre makeGenre = new Genre(id);
        makeGenre.setName(GENRES.valueOf(name));
        return makeGenre;
    }

    public void saveGenres(List<Genre> genres, long filmId) {
        String sqlQuery = "MERGE INTO GENRE_FILM (FILM_ID, GENRE_ID) KEY (FILM_ID, GENRE_ID) " +
                "VALUES (?, ?)";
        genres.forEach(s -> jdbcTemplate.update(sqlQuery, filmId, s.getId()));
    }

    public List<Genre> makeFilmGenres(Integer filmId) {
        String sqlQuery = "SELECT GENRE_ID FROM GENRE_FILM WHERE FILM_ID=?";
        return jdbcTemplate.queryForList(sqlQuery, Integer.class, filmId).stream()
                .map(this::getGenreById)
                .collect(Collectors.toList());
    }

    public void updateGenres(Film film) {
        String sqlQuery = "DELETE FROM GENRE_FILM WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
        if (!film.getGenres().isEmpty()) {
            saveGenres(film.getGenres(), film.getId());
        }
    }

}
