package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.List;

@RestController
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    private static final Logger log = LoggerFactory.getLogger(RatingController.class);

    @GetMapping("/mpa/{id}")
    public Rating getRatingById(@PathVariable int id) {
        log.info("Жанр по ИД {}", ratingService.getRatingById(id));
        return ratingService.getRatingById(id);
    }
    @GetMapping("/mpa")
    public List<Rating> getAllRating() {
        log.info("Все рейтинги {}", ratingService.getAllRating());
        return ratingService.getAllRating();
    }
}

