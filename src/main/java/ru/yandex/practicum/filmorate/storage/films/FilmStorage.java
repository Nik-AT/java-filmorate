package ru.yandex.practicum.filmorate.storage.films;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmStorage {
    List<Film> getFilm();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(long id);

    void addLike(Film filmById, User user);

    void deleteLike(Film filmById, User user);
}
