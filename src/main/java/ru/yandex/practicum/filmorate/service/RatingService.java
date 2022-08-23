package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.films.RatingStorage;

import java.util.List;

@Service
public class RatingService {
    private final RatingStorage ratingStorage;

    @Autowired
    public RatingService(RatingStorage ratingStorage) {
        this.ratingStorage = ratingStorage;
    }

    public Rating getRatingById(Integer id) {
        if (id < 0) {
            throw new NotFoundException("Жанр с ИД: " + id + " не существует");
        }
        return ratingStorage.getRatingById(id);
    }

    public List<Rating> getAllRating() {
        return ratingStorage.getAllRating();
    }
}
