package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.films.GenreDb;

import java.util.List;

@Service
public class GenreService {
    private final GenreDb genreDb;

    @Autowired
    public GenreService(GenreDb genreDb) {
        this.genreDb = genreDb;
    }

    public Genre getGenreById(Integer id) {
        if (id < 0 || id > 6) {
            throw new NotFoundException("Жанр с ИД: " + id + " не существует");
        }
        return genreDb.getGenreById(id);
    }

    public List<Genre> getAllGenre() {
        return genreDb.getAllGenre();
    }
}
