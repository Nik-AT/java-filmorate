package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.films.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getGenreById(Integer id) {
        if (id < 0) {
            throw new NotFoundException("Жанр с ИД: " + id + " не существует");
        }
        return genreStorage.getGenreById(id);
    }

    public List<Genre> getAllGenre() {
        return genreStorage.getAllGenre();
    }
}
