package ru.yandex.practicum.filmorate.storage.films;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class FilmDb implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDb genreDb;
    private final RatingDb ratingDao;

    @Autowired
    public FilmDb(JdbcTemplate jdbcTemplate, GenreDb genreDb, RatingDb ratingDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDb = genreDb;
        this.ratingDao = ratingDao;

    }

    @Override
    public List<Film> getFilm() {
        String sqlQuery = "SELECT * FROM FILMS";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());
        sqlQuery = "SELECT FILM_ID FROM FILMS ORDER BY FILM_ID DESC LIMIT 1";
        Long filmId = jdbcTemplate.queryForObject(sqlQuery, Long.class);
        if (film.getGenres() != null) {
            genreDb.saveGenres(film.getGenres(), filmId);
        }
        film.setId(filmId);
        return getFilmById(filmId);
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE FILMS SET NAME=?, DESCRIPTION =?, RELEASE_DATE=?, " +
                "DURATION=?, RATING_ID=? where FILM_ID=?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        if (film.getGenres() != null) {
            genreDb.updateGenres(film);
        }
        Film filmById = getFilmById(film.getId());
        if (film.getGenres() != null && filmById.getGenres() == null) {
            filmById.setGenres(new ArrayList<>());
            return filmById;
        }
        return filmById;
    }

    @Override
    public Film getFilmById(long id) {
        String sqlQuery = "SELECT * FROM FILMS WHERE FILM_ID =?";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeFilm(rs), id);
    }

    @Override
    public void addLike(Film filmById, User user) {
        String sqlQuery = "MERGE INTO LIKES (FILM_ID, USER_ID) KEY (FILM_ID, USER_ID)" +
                "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery,
                filmById.getId(),
                user.getId());
    }

    @Override
    public void deleteLike(Film filmById, User user) {
        String sqlQuery = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID=?";
        jdbcTemplate.update(sqlQuery,
                filmById.getId(),
                user.getId());
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("FILM_ID");
        String name = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");
        LocalDate releaseDate = rs.getDate("RELEASE_DATE").toLocalDate();
        Integer duration = rs.getInt("DURATION");
        Integer ratingId = rs.getInt("RATING_ID");
        Rating mpa = ratingDao.getRatingById(ratingId);
        Film makeFilm = new Film(id, name, description, releaseDate, duration, mpa);
        makeFilm.getUserIdLike().addAll(allLikes(id));
        if (!genreDb.makeFilmGenres(id).isEmpty()) {
            makeFilm.setGenres(genreDb.makeFilmGenres(id));
        }
        return makeFilm;
    }

    public List<Long> allLikes(Integer filmId) {
        String sqlQuery = "SELECT USER_ID FROM LIKES  WHERE FILM_ID = ?";
        return jdbcTemplate.queryForList(sqlQuery, Long.class, filmId);
    }

}
