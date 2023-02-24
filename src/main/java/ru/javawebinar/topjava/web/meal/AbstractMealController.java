package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.*;
import static ru.javawebinar.topjava.web.SecurityUtil.*;

public class AbstractMealController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;

    Meal save(Meal meal, int userId){
        checkNotFoundWithId(authUserId()==userId,userId);
        checkNotFound(meal,"userId %d ".formatted(userId));

        return service.save(meal,userId);
    }

    boolean delete(int id, int userId){
        checkNotFoundWithId(authUserId()==userId,userId);

        return service.delete(id,userId);

    }

    Meal get(int id, int userId){
        checkNotFoundWithId(authUserId()==userId,userId);

        return service.get(id,userId);
    }

    Collection<MealTo> getAll(int userId){
        checkNotFoundWithId(authUserId()==userId,userId);

        return  MealsUtil.getTos(service.getAll(userId),authUserCaloriesPerDay());

    }
}