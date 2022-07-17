package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> filmMap = new HashMap();
    private static long idFilms;

    @Override
    public ArrayList<Film> getFilm() {
        return new ArrayList<>(filmMap.values());
    }

    @Override
    public Film createFilm(Film film) {
        idFilms++;
        film.setId(idFilms);
        filmMap.put(idFilms, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        long id = film.getId();
        filmMap.put(id, film);
        return film;
    }

    @Override
    public Map<Long, Film> getFilmMap() {
        return filmMap;
    }

    @Override
    public Film getFilmById(long id) {
        return filmMap.get(id);
    }

    @Override
    public void addLike(Film filmById, User user) {
        filmById.getUserIdLike().add(user.getId());
    }

    @Override
    public void deleteLike(Film filmById, User user) {
        filmById.getUserIdLike().remove(user.getId());
    }
}
