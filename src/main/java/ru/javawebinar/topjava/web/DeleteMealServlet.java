package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.InMemoryMealRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteMealServlet extends HttpServlet {
    MealRepository mealRepository = InMemoryMealRepositoryImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       int mealId = Integer.parseInt(req.getParameter("id"));
       mealRepository.delete(mealId);
       resp.sendRedirect("meals");
    }
}
