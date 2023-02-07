package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.JspHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/edit")
public class EditMealServlet extends HttpServlet {
    MealRepository mealRepository = InMemoryMealRepositoryImpl.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getParameter("id"))){
            req.setAttribute("meal",mealRepository.read(Integer.parseInt(req.getParameter("id"))).orElse(null));
        }
        req.getRequestDispatcher(JspHelper.getPath("mealForm")).forward(req,resp);
    }
}
