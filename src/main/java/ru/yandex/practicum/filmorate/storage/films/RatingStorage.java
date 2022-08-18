package ru.yandex.practicum.filmorate.storage.films;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

public interface RatingStorage {
    Rating getRatingById(Integer ratingId);

    List<Rating> getAllRating();
}
