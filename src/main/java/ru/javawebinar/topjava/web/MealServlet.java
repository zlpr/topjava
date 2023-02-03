package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.JspHelper;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealTo> mealTos = MealsUtil.filteredByStreams(MealsUtil.MEALS,MealsUtil.CALORIES_PER_DAY);
        req.setAttribute("meals",mealTos);
        req.getRequestDispatcher(JspHelper.gerPath("meals"))
                .forward(req,resp);
    }
}
