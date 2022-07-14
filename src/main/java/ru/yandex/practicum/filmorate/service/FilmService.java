package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;
import java.util.Map;

@Service
public class FilmService {

    @Autowired
    final FilmStorage filmStorage;

    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.filmStorage = inMemoryFilmStorage;
    }

    public List<Film> getFilm() {
        return filmStorage.getFilm();
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        // todo
        return filmStorage.updateFilm(film);
    }
    public Map<Long, Film> getFilmMap(){
        return filmStorage.getFilmMap();
    }


}
