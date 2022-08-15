package ru.yandex.practicum.filmorate.storage.films;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Map;

public interface FilmStorageDb {
    ArrayList<Film> getFilm();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Map<Long, Film> getFilms();

    Film getFilmById(long id);

    void addLike(Film filmById, User user);

    void deleteLike(Film filmById, User user);
}
