package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {

@Autowired
    final InMemoryFilmStorage inMemoryFilmStorage;

    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public List<Film> getFilm() {
        return inMemoryFilmStorage.getFilm();
    }
    public Film createFilm(Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }
    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }


}
