package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(0, 0), 2000);
        // mealsTo.forEach(System.out::println);

        filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000).forEach(System.out::println);


    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = new HashMap<>();
        List<UserMealWithExcess> dtos = new ArrayList<>(meals.toArray().length);

        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            map.merge(date, meal.getCalories(), Integer::sum);
        }

        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExcess dto = new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), map.get(date) > caloriesPerDay);
                dtos.add(dto);
            }
        }

        return dtos;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> cd = meals.stream()
                .collect(groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> mapFromUserMealWithExcess(caloriesPerDay, cd, m))
                .collect(toList());

//        Predicate<UserMeal> mealFilter = m ->
//                TimeUtil.isBetweenHalfOpen(
//                        m.getDateTime().toLocalTime(), startTime, endTime);
//
//        return meals.stream().collect(
//                collectingAndThen(groupingBy(e -> e.getDateTime().toLocalDate(),
//                                teeing(reducing(0, UserMeal::getCalories, Integer::sum),
//                                        filtering(mealFilter, toList()),
//                                        (s, n) -> n.stream().
//                                                map(m -> new UserMealWithExcess(
//                                                        m.getDateTime(),
//                                                        m.getDescription(),
//                                                        m.getCalories(),
//                                                        s > caloriesPerDay))
//                                                .collect(toList()))),
//                        (i) -> i.values().stream().
//                                flatMap(Collection::stream)
//                                .collect(toList())));
    }

    private static UserMealWithExcess mapFromUserMealWithExcess(int caloriesPerDay, Map<LocalDate, Integer> cd, UserMeal m) {
        return new UserMealWithExcess(m.getDateTime(),
                m.getDescription(),
                m.getCalories(),
                cd.get(m.getDateTime().toLocalDate()) > caloriesPerDay);
    }


}

