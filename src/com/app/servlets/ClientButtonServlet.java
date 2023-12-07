package com.app.servlets;

import com.app.entity.Customer;
import com.app.entity.Worker;
import com.app.service.ButtonService;
import com.app.service.ClientButtonService;
import com.app.service.ClientService;
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

@WebServlet("/buttonClient")
public class ClientButtonServlet extends HttpServlet {

    private final ClientButtonService clientButtonService = ClientButtonService.getInstance();
    private final ClientService clientService = ClientService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String clickedButton = "";
        var loggedCustomer = (Customer) session.getAttribute("loggedIn");
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
            case "buttonCreateOrder": {
                session.setAttribute("active", "addOrder");
                session.setAttribute("destinations", clientButtonService.getAllRoutes());
                resp.sendRedirect("/client");
                break;
            }
            case "buttonChangeOrder": {
                session.setAttribute("active", "changeOrder");
                session.setAttribute("currentOrdersToChange", clientService.getCurrentOrders(loggedCustomer).stream().map(v -> v.get()).toList());
                resp.sendRedirect("/client");
                break;
            }
            case "buttonRemoveOrder": {
                session.setAttribute("active", "removeOrder");
                session.setAttribute("currentOrdersToRemove", clientService.getCurrentOrders(loggedCustomer).stream().map(v -> v.get().toString()).toList());
                resp.sendRedirect("/client");
                break;
            }
            case "buttonChangePassword": {
                session.setAttribute("active", "changePassword");
                resp.sendRedirect("/client");
                break;
            }
            case "buttonSearchOrders":{
                session.setAttribute("active","showOrderSearch");
                session.setAttribute("statusList", clientService.getAllOrders(loggedCustomer).stream().map(val -> val.get().getStatus()).distinct().toList());
                resp.sendRedirect("/client");
                break;
            }
        }
    }
}