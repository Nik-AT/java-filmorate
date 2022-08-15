package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    private static final Logger log = LoggerFactory.getLogger(GenreController.class);

    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable int id) {
        log.info("Жанр по ИД {}", genreService.getGenreById(id));
        return genreService.getGenreById(id);
    }
    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        log.info("Все жанры {}", genreService.getAllGenre());
        return genreService.getAllGenre();
    }
}
