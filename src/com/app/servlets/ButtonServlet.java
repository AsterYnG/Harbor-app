package com.app.servlets;

import com.app.entity.Worker;
import com.app.service.ButtonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@WebServlet("/button")
public class ButtonServlet extends HttpServlet {

    private final ButtonService buttonService = ButtonService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String clickedButton = "";
        Map<String, Boolean> buttons = new HashMap<>();
        var incomingParamatres = req.getParameterMap();
        for (String s : incomingParamatres.keySet()) { // Занос всех параметров button% в Map
            if (s.startsWith("button")) buttons.put(s, Boolean.valueOf(req.getParameter(s)));
        }


        Optional<Map.Entry<String, Boolean>> found = buttons.entrySet().stream()
                .filter(entry -> entry.getValue().equals(true))
                .findFirst();
        clickedButton = found.isPresent() ? found.get().getKey() : "";

        switch (clickedButton) {
            case "buttonAddEmployee": {

            }
            case "buttonShowAllEmployees": {
                var workers = buttonService.showAllEmployees();
                session.setAttribute("workers", workers);
                Set<String> columnNames = new HashSet<>();
                for (Field declaredField : workers.get(0).getClass().getDeclaredFields()) {
                    columnNames.add(declaredField.getName());
                }
                session.setAttribute("columnNames", columnNames);
                session.setAttribute("active","showWorkers");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonAddFreighter": {
                break;
            }
            case "buttonShowAllFreighters": {
                var freighters = buttonService.showAllFreighters();
                session.setAttribute("freighters", freighters);
                Set<String> columnNames = new HashSet<>();
                for (Field declaredField : freighters.get(0).getClass().getDeclaredFields()) {
                    columnNames.add(declaredField.getName());
                }
                session.setAttribute("columnNames", columnNames);
                session.setAttribute("active","showFreighters");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonShowAllClients": {
                break;

            }
            case "buttonShowVoyageLog": {
                break;
            }
            case "buttonShowAllTeams": {
                break;
            }
        }
    }
}
