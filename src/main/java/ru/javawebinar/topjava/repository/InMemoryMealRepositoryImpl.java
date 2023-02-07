package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class InMemoryMealRepositoryImpl implements MealRepository{
    private static final InMemoryMealRepositoryImpl INSTANCE = new InMemoryMealRepositoryImpl();
    private final Map<Integer,Meal> data;
    private final AtomicInteger sequence;

    static {
        INSTANCE.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        INSTANCE.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        INSTANCE.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        INSTANCE.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        INSTANCE.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        INSTANCE.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    private InMemoryMealRepositoryImpl() {
        data = new ConcurrentHashMap<>();
        sequence = new AtomicInteger();
    }

    public static InMemoryMealRepositoryImpl getInstance(){
        return INSTANCE;
    }

    @Override
    public Meal create(Meal meal){
        Objects.requireNonNull(meal);
        if (meal.getId()!= null){
            throw new RuntimeException("");
        }

        Meal mealNew = new Meal(sequence.incrementAndGet(),
                    meal.getDateTime(),
                    meal.getDescription(),
                    meal.getCalories());

        return data.put(mealNew.getId(),mealNew);
    }

    @Override
    public Optional<Meal> read(int id){
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Meal update(Meal meal){
        Objects.requireNonNull(meal);

        return data.put(meal.getId(),meal);
    }

    @Override
    public void delete(int id){
        data.remove(id);
    }

    @Override
    public List<Meal> readAll(){
        return new ArrayList<>(data.values());
    }
}
