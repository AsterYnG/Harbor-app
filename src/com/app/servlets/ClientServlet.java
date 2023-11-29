package com.app.servlets;

import com.app.dto.CreateCargoDto;
import com.app.dto.CreatePassportDto;
import com.app.dto.FreighterDto;
import com.app.dto.ShowCustomerDto;
import com.app.entity.*;
import com.app.exceptions.ValidationException;
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
        var history = clientService.getOrderHistory(loggedCustomer);
        var curOrders = clientService.getCurrentOrders(loggedCustomer);
        var parameterMap = request.getParameterMap();

        var orderHistory = history.stream()
                .map(Optional::get)
                .distinct()
                .collect(Collectors.toList());
        session.setAttribute("orderHistory", orderHistory);
//         получаем историю заказов из сервиса

        var currentOrders = curOrders.stream()
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
                    //проверяем чекбокс (как по другому сделать я не придумал) =)
                    boolean isFragile;
                    try {
                        isFragile = Boolean.valueOf(parameterMap.get("isFragile")[0]);
                        if (isFragile == false) {
                            isFragile = true;
                        }
                    } catch (NullPointerException e) {
                        isFragile = false;
                    }

                    var order = CreateCargoDto.builder()
                            .cargoWeight(Integer.valueOf(parameterMap.get("cargoWeight")[0]))
                            .isFragile(isFragile)
                            .cargoSize(Integer.valueOf(parameterMap.get("cargoSize")[0]))
                            .build();

                    session.setAttribute("order", order);
                    List<FreighterDto> freightersWithShippingPrice = new ArrayList<>();
                    List<Freighter> availableFreighters = clientService.getAvailableFreighters(destination.getDestinationCity());
                    for (Freighter freighter : availableFreighters) {
                        double shippingPrice = clientService.calculateShippingCost(order, freighter);
                        FreighterDto freighterWithShippingPrice = new FreighterDto(
                                freighter.getTax(),
                                freighter.getWeightCost(),
                                freighter.getSizeCost(),
                                freighter.getFragileCost(),
                                freighter.getFreighterName(),
                                shippingPrice
                        );
                        freightersWithShippingPrice.add(freighterWithShippingPrice);
                    }
                    session.setAttribute("availableFreighters", freightersWithShippingPrice);

                    session.setAttribute("active", createOrderStatus.get(createOrderStatus.indexOf(currentStatus) + 1));
                    response.sendRedirect(request.getContextPath() + "/client"); // Перенаправление на ту же страницу
                    break;
                }

                case "addFreighter": {
                    var availableFreighter = clientService.getFreighterByName(request.getParameter("freighterName"));
                    session.setAttribute("freighter", availableFreighter);
                    session.setAttribute("active"," ");
                    var order = (CreateCargoDto) session.getAttribute("order");
                    var destination = (Route)session.getAttribute("destinationCity");
                    clientService.createOrder(order, availableFreighter, loggedCustomer, destination.getDestinationCountry());
                    response.sendRedirect(request.getContextPath() + "/client"); // Перенаправление на ту же страницу
                    break;
                }
            }
        }
        else if (currentStatus.equals("changePassword")) {
            session.setAttribute("tempOldPassword", request.getParameter("oldPassword"));
            try { // Валидация
                var currentPassword = ShowCustomerDto.builder()
                        .login(loggedCustomer.getLogin())
                        .password(String.valueOf(session.getAttribute("tempOldPassword")))
                        .build();
                clientService.checkPassword(currentPassword);
            } catch (ValidationException e) {
                request.setAttribute("errors", e.getErrors());
                doGet(request, response);
            }

            session.setAttribute("tempNewPassword", request.getParameter("newPassword"));

            var newPassword = ShowCustomerDto.builder()
                            .password(String.valueOf(session.getAttribute("tempNewPassword")))
                            .login(loggedCustomer.getLogin())
                            .build();

            clientService.updatePassword(newPassword);
            session.removeAttribute("tempNewPassword");
            session.removeAttribute("tempOldPassword");
            session.setAttribute("active", "");
            response.sendRedirect(request.getContextPath() + "/client"); // Перенаправление на ту же страницу
        }
    }
}