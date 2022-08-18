package ru.yandex.practicum.filmorate.storage.films;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    Genre getGenreById(Integer genreId);

    List<Genre> getAllGenre();
}
