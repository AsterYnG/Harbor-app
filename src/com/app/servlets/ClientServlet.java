package com.app.servlets;

import com.app.dto.CreateCargoDto;
import com.app.dto.CreatePassportDto;
import com.app.entity.*;
import com.app.service.ClientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    private final ClientService clientService = ClientService.getInstance();

    private final List<String> createOrderStatus = new ArrayList<>();

    {
        createOrderStatus.add("addOrder");
        createOrderStatus.add("addFreighter");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        var loggedCustomer = (Customer) session.getAttribute("loggedIn");
        var customerId = clientService.getOrderHistory(loggedCustomer);
        var parameterMap = request.getParameterMap();

        var orderHistory = customerId.stream()
                .map(Optional::get)
                .distinct()
                .collect(Collectors.toList());
        session.setAttribute("orderHistory", orderHistory);
//         получаем историю заказов из сервиса

        var currentOrders = customerId.stream()
                .map(Optional::get)
                .distinct()
                .collect(Collectors.toList());
        session.setAttribute("currentOrders", currentOrders);
//         получаем текущие заказы из сервиса


        request.getRequestDispatcher("/WEB-INF/jsp/clientPage.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        var loggedCustomer = (Customer) session.getAttribute("loggedIn");
        var parameterMap = request.getParameterMap();

        var currentStatus = (String) session.getAttribute("active");
        if (createOrderStatus.contains(currentStatus)) {
            switch (currentStatus) {
                case "addOrder": {
                    var destination = clientService.findByRouteName(request.getParameter("destinationRoute")).get();
                    session.setAttribute("destinationCity", destination);

                    var order = CreateCargoDto.builder()
                            .cargoWeight(Integer.valueOf(parameterMap.get("cargoWeight")[0]))
                            .isFragile(Boolean.valueOf(parameterMap.get("isFragile")[0]))
                            .cargoSize(Integer.valueOf(parameterMap.get("cargoSize")[0]))
                            .build();

                    session.setAttribute("order", order);
                    session.setAttribute("availableFreighters", clientService.getAvailableFreighters(destination.getDestinationCity()));
                    session.setAttribute("active", createOrderStatus.get(createOrderStatus.indexOf(currentStatus) + 1));
                    request.getRequestDispatcher("/WEB-INF/jsp/clientPage.jsp")
                            .forward(request, response);
                    break;
                }

                case "addFreighter": {
                    var availableFreighter = clientService.getFreighterByName(request.getParameter("freighterName"));
                    session.setAttribute("freighterName", availableFreighter);
                    session.setAttribute("active"," ");
                    var order = (CreateCargoDto) session.getAttribute("order");
                    var destination = (Route)session.getAttribute("destinationCity");
                    clientService.createOrder(order, availableFreighter, loggedCustomer, destination.getDestinationCountry());
                    request.getRequestDispatcher("/WEB-INF/jsp/clientPage.jsp")
                            .forward(request, response);
                    break;
                }
            }
        }
    }
}