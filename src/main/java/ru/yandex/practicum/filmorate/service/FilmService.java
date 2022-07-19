package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.filmStorage = inMemoryFilmStorage;
        this.userStorage = inMemoryUserStorage;
    }

    public List<Film> getFilm() {
        return filmStorage.getFilm();
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Map<Long, Film> getFilmMap() {
        return filmStorage.getFilms();
    }

    public void addLikeToFilm(long id, long userId) {
        Film filmById = filmStorage.getFilmById(id);
        User user = userStorage.getUserById(userId);
        if (id < 0 || filmById == null) {
            throw new NotFoundException("Не найден фильм с ИД: " + id);
        }
        if (userId < 0 || user == null) {
            throw new NotFoundException("Не найден пользователь с ИД: " + id);
        }
        filmStorage.addLike(filmById, user);
    }

    public Film getFilmById(long id) {
        Film filmById = filmStorage.getFilmById(id);
        if (filmById == null) {
            throw new NotFoundException("Фильм с ИД: " + id + " не найден");
        }
        return filmStorage.getFilmById(id);
    }

    public void deleteLike(long id, long userId) {
        Film filmById = filmStorage.getFilmById(id);
        User user = userStorage.getUserById(userId);
        if (id < 0 || filmById == null) {
            throw new NotFoundException("Не найден фильм с ИД: " + id);
        }
        if (userId < 0 || user == null) {
            throw new NotFoundException("Не найден пользователь с ИД: " + id);
        }
        filmStorage.deleteLike(filmById, user);
    }

    public List<Film> popularFilm(Integer count) {
        ArrayList<Film> film = filmStorage.getFilm();
        return film
                .stream()
                .sorted(((o1, o2) -> o2.getUserIdLike().size() - o1.getUserIdLike().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
