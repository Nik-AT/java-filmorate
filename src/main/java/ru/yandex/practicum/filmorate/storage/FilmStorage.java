package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    ArrayList<Film> getFilm();

    Film createFilm(Film film);

    Film updateFilm(Film film);

}
