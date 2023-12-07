package com.app.servlets;

import com.app.dto.CreateCargoDto;
import com.app.dto.CreatePassportDto;
import com.app.dto.FreighterDto;
import com.app.dto.ShowCustomerDto;
import com.app.entity.*;
import com.app.exceptions.ValidationException;
import com.app.service.ClientButtonService;
import com.app.service.ClientService;
import com.app.validator.Error;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    private final ClientService clientService = ClientService.getInstance();
    private final ClientButtonService clientButtonService = ClientButtonService.getInstance();

    private final List<String> createOrderStatus = new ArrayList<>();

    {
        createOrderStatus.add("addOrder");
        createOrderStatus.add("addFreighterToOrder");
    }
    private final List<String> changeOrderStatus = new ArrayList<>();

    {
        changeOrderStatus.add("changeOrder");
        changeOrderStatus.add("changeOrderCharacteristics");
        changeOrderStatus.add("addFreighterToChangedOrder");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        var loggedCustomer = (Customer) session.getAttribute("loggedIn");
        var history = clientService.getOrderHistory(loggedCustomer);
        var curOrders = clientService.getCurrentOrders(loggedCustomer);

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

        if (Boolean.valueOf(request.getParameter("exit")).equals(true)) {
            session.setAttribute("active", "");
            doGet(request, response);
        }

        if (changeOrderStatus.contains(currentStatus)) {
            switch (currentStatus) {
                case "changeOrder": {
                    var wannabeChangedOrder = clientService.findByOrderId(Integer.valueOf(request.getParameter("clientCurrentOrders"))).get();
                    session.setAttribute("clientsOrder", wannabeChangedOrder);

                    var curCargo = clientService.cargoByOrderId(Integer.valueOf(request.getParameter("clientCurrentOrders")));
                    session.setAttribute("currentWeight", curCargo.getCargoWeight());
                    session.setAttribute("currentSize", curCargo.getCargoSize());
                    session.setAttribute("currentIsFragile", curCargo.getIsFragile());
                    session.setAttribute("destinationCity", curCargo.getDestination());

                    session.setAttribute("destinations", clientButtonService.getAllRoutes());

                    session.setAttribute("active", changeOrderStatus.get(changeOrderStatus.indexOf(currentStatus) + 1));
                    response.sendRedirect(request.getContextPath() + "/client"); // Перенаправление на ту же страницу
                    break;
                }
                case "changeOrderCharacteristics": {
                    var destination = clientService.findByRouteName(request.getParameter("correctedDestinationRoute")).get();
                    session.setAttribute("correctedDestination", destination);
                    //проверяем чекбокс (как по другому сделать я не придумал) =)
                    boolean isFragile;
                    try {
                        isFragile = Boolean.parseBoolean(parameterMap.get("correctedIsFragile")[0]);
                        if (!isFragile) {
                            isFragile = true;
                        }
                    } catch (NullPointerException e) {
                        isFragile = false;
                    }

                    var order = CreateCargoDto.builder()
                            .cargoWeight(Integer.valueOf(parameterMap.get("correctedCargoWeight")[0]))
                            .isFragile(isFragile)
                            .cargoSize(Integer.valueOf(parameterMap.get("correctedCargoSize")[0]))
                            .build();

                    session.setAttribute("correctedOrder", order);
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
                    session.setAttribute("newAvailableFreighters", freightersWithShippingPrice);

                    session.setAttribute("active", changeOrderStatus.get(changeOrderStatus.indexOf(currentStatus) + 1));
                    response.sendRedirect(request.getContextPath() + "/client"); // Перенаправление на ту же страницу
                    break;
                }
                case "addFreighterToChangedOrder": {
                    var availableFreighter = clientService.getFreighterByName(request.getParameter("correctedFreighterName"));
                    session.setAttribute("freighter", availableFreighter);
                    session.setAttribute("active"," ");
                    var orderEntity = (Order) session.getAttribute("clientsOrder");
                    var order = (CreateCargoDto) session.getAttribute("correctedOrder");
                    var destination = (Route)session.getAttribute("correctedDestination");
                    clientService.updateOrder(order, availableFreighter, loggedCustomer, destination.getDestinationCity(), orderEntity);
                    response.sendRedirect(request.getContextPath() + "/client"); // Перенаправление на ту же страницу
                    break;
                }
            }
        }

        else if (createOrderStatus.contains(currentStatus)) {
            switch (currentStatus) {
                case "addOrder": {
                    var destination = clientService.findByRouteName(request.getParameter("destinationRoute")).get();
                    session.setAttribute("destinationCity", destination);
                    //проверяем чекбокс (как по другому сделать я не придумал) =)
                    boolean isFragile;
                    try {
                        isFragile = Boolean.valueOf(parameterMap.get("isFragile")[0]);
                        if (!isFragile) {
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

                case "addFreighterToOrder": {
                    var availableFreighter = clientService.getFreighterByName(request.getParameter("freighterName"));
                    session.setAttribute("freighter", availableFreighter);
                    session.setAttribute("active"," ");
                    var order = (CreateCargoDto) session.getAttribute("order");
                    var destination = (Route)session.getAttribute("destinationCity");
                    clientService.createOrder(order, availableFreighter, loggedCustomer, destination.getDestinationCity());
                    response.sendRedirect(request.getContextPath() + "/client"); // Перенаправление на ту же страницу
                    break;
                }
            }
        }
        else if (currentStatus.equals("removeOrder")) {
            var wannabeRemovedOrder = clientService.findByOrderId(Integer.valueOf(request.getParameter("clientCurrentOrdersToRemove"))).get();
            clientService.deleteOrder(wannabeRemovedOrder);

            session.setAttribute("active", " ");
            response.sendRedirect(request.getContextPath() + "/client"); // Перенаправление на ту же страницу
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
        else if (currentStatus.equals("showOrderSearch")) {

            Customer client = loggedCustomer;
            Integer orderId;

            LocalDateTime orderDateFrom;
            LocalDateTime orderDateTo;

            var statusList = clientService.getAllOrders(client).stream().map(val -> val.get().getStatus()).distinct().toList();

            if (!request.getParameter("orderIdByClient").isBlank()) {
                orderId = Integer.parseInt(request.getParameter("orderIdByClient"));
            } else orderId = null;

            if (!request.getParameter("orderDateFromByClient").isBlank()) {
                orderDateFrom = LocalDateTime.parse(request.getParameter("orderDateFromByClient"));
            } else orderDateFrom = LocalDateTime.parse("1930-01-01T00:00:00");

            if (!request.getParameter("orderDateToByClient").isBlank()) {
                orderDateTo = LocalDateTime.parse(request.getParameter("orderDateToByClient"));
            } else orderDateTo = LocalDateTime.parse("2024-01-01T00:00:00");

            if (orderDateFrom.compareTo(orderDateTo) > 0) {
                request.setAttribute("error", Error.of("invalid.interval.jsp","Неправильно указан промежуток от > до"));
                doGet(request,response);
            }

            var statusListResult = statusList.stream().filter(value -> request.getParameter(value) != null)
                    .toList();
            if (statusListResult.isEmpty()) {
                statusListResult = statusList;
            }

            var filteredOrders = clientService.showOrdersSearch(client, orderId, orderDateFrom, orderDateTo, statusListResult);
            List<Optional<Order>> filteredOrdersResult = new ArrayList<>();
            for (List<Optional<Order>> value : filteredOrders.values()) {
                filteredOrdersResult.add(value.get(0));
            }
            session.setAttribute("searchResult", filteredOrdersResult.stream().map(v -> v.get().toString()).toList());
            session.setAttribute("active", "showSearchResultOrdersForCustomer");
            response.sendRedirect("/client");
        }
    }
}