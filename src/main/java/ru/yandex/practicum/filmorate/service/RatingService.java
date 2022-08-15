package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.films.RatingDb;

import java.util.List;

@Service
public class RatingService {
    private final RatingDb ratingDb;

    @Autowired
    public RatingService(RatingDb ratingDb) {
        this.ratingDb = ratingDb;
    }

    public Rating getRatingById(Integer id) {
        if (id < 0 || id > 5) {
            throw new NotFoundException("Жанр с ИД: " + id + " не существует");
        }
        return ratingDb.getRatingById(id);
    }
    public List<Rating> getAllRating() {
        return ratingDb.getAllRating();
    }
}
