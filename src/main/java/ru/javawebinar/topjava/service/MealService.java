package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.*;
@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal, int userId) {
        return checkNotFound(repository.save(meal, userId), "ops");
    }

    public boolean delete(int id, int userId) {
        return repository.delete(id, userId);

    }

    public Meal get(int id, int userId) {
        return checkNotFound(repository.get(id, userId), "ops");
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}