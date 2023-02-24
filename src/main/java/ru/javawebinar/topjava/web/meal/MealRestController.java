package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.util.Collection;

import static ru.javawebinar.topjava.web.SecurityUtil.*;
@Controller
public class MealRestController extends AbstractMealController {


    public Meal save(Meal meal) {
        return super.save(meal, authUserId());
    }

    public boolean delete(int id) {
        return super.delete(id, authUserId());
    }

    public Meal get(int id) {
        return super.get(id, authUserId());
    }

    public Collection<MealTo> getAll() {
        return super.getAll(authUserId());
    }

}