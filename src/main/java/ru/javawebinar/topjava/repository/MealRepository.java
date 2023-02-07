package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public interface MealRepository {
    Meal create(Meal meal);
    Optional<Meal> read(int id);
    Meal update(Meal meal);
    void delete(int id);
    List<Meal> readAll();
}
