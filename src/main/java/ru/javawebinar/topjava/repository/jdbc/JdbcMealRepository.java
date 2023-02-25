package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcMealRepository implements MealRepository {
    private final RowMapper rowMapper;

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meal")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        rowMapper = new MealRowMapper();
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", meal.getId());
        map.put("description", meal.getDescription());
        map.put("calories", meal.getCalories());
        map.put("date_time", meal.getDateTime());
        map.put("user_id", userId);

        if (meal.isNew()) {
            Number newId = insertMeal.executeAndReturnKey(map);
            meal.setId(newId.intValue());
        } else {
            String query = "UPDATE meal  SET description=:description, calories=:calories, date_time=:date_time " +
                    " WHERE id=:id AND user_id=:user_id";
            if (namedParameterJdbcTemplate.update(query, map) == 0) {
                return null;
            }
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        String query = "DELETE FROM meal WHERE id=? AND user_id=?";
        return jdbcTemplate.update(query, id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        String query = "SELECT id,date_time,description,calories FROM meal WHERE id = ? AND user_id = ?";
        List<Meal> meal = jdbcTemplate.query(query, rowMapper, id, userId);
        return DataAccessUtils.singleResult(meal);
    }

    @Override
    public List<Meal> getAll(int userId) {
        String query = "SELECT id,date_time,description,calories FROM meal WHERE user_id = ?";
        List<Meal> meals = jdbcTemplate.query(query, rowMapper, userId);
        return meals;

    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        String query = "SELECT id,date_time,description,calories FROM meal WHERE user_id=?  AND date_time >=  ? AND date_time < ? ORDER BY date_time DESC";
        List<Meal> meals = jdbcTemplate.query(query, rowMapper, userId);
        return meals;
    }

    public static class MealRowMapper implements RowMapper<Meal> {
        @Override
        public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer id = rs.getObject("id", Integer.class);
            LocalDateTime dateTime = rs.getObject("date_time", LocalDateTime.class);
            String description = rs.getObject("description", String.class);
            Integer calories = rs.getObject("calories", Integer.class);

            return new Meal(id, dateTime, description, calories);
        }
    }
}


