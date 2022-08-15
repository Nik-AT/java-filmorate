package ru.yandex.practicum.filmorate.storage.films;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Map;

@Component
@Primary
public class FilmDb implements FilmStorageDb {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;

    @Autowired
    public FilmDb(JdbcTemplate jdbcTemplate, GenreDao genreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDao = genreDao;
    }

    @Override
    public ArrayList<Film> getFilm() {
        return null;
    }

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "insert into films (name, description, release_Date, duration) " +
                "values (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRating().getId());
        sqlQuery = "select film_id from films order by film_id desc limit 1";
        Integer filmId = jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        if (film.getGenres() != null) {
            genreDao.saveGenres(film.getGenres(), filmId);
        }
        film.setId(filmId);
        return getFilmById(filmId);
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public Map<Long, Film> getFilms() {
        return null;
    }

    @Override
    public Film getFilmById(long id) {
        return null;
    }

    @Override
    public void addLike(Film filmById, User user) {

    }

    @Override
    public void deleteLike(Film filmById, User user) {

    }
}
