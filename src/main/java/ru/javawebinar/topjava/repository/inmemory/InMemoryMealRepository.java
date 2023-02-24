package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> rep = repository.computeIfAbsent(userId, r -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            rep.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return rep.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> rep = repository.get(userId);
        return rep.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> rep = repository.get(userId);
        return rep.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> rep = repository.get(userId);
        return rep.values();
    }
}

