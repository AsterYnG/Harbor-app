package com.app.servlets;

import com.app.dto.FreighterDto;
import com.app.entity.VoyageLog;
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
import java.time.LocalDateTime;
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
                session.setAttribute("active","addEmployee");
                session.setAttribute("positions",buttonService.getAllPositions());
                resp.sendRedirect("/admin");
                break;
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
                session.setAttribute("active","addFreighter");
                resp.sendRedirect("/admin");
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
                var customers = buttonService.showAllCustomers();
                session.setAttribute("customers", customers);
                Set<String> columnNames = new HashSet<>();
                for (Field declaredField : customers.get(0).getClass().getDeclaredFields()) {
                    columnNames.add(declaredField.getName());
                }
                session.setAttribute("columnNames", columnNames);
                session.setAttribute("active","showCustomers");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonShowVoyageLog": {
                List<VoyageLog> voyageLog = buttonService.getVoyageLog();
                session.setAttribute("voyageLog", voyageLog);
                LocalDateTime temp;

                session.setAttribute("active","showVoyageLog");
                resp.sendRedirect("/admin");

                break;
            }
            case "buttonAddAvailableRoute": {
                session.setAttribute("active","addAvailableRoute");
                List<FreighterDto> freighters = buttonService.showAllFreighters();
                session.setAttribute("freighters",freighters);
                resp.sendRedirect("/admin");
                break;

            }
            case "buttonSearch":{
                session.setAttribute("active","showSearch");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonSort":{
                var currentStatus = session.getAttribute("active");
                switch (currentStatus){
                    case "showSearchResultAvailableRoutes":{
                        session.getAttribute("searchResult");

                    }
                }
            }
        }
    }
}
