package com.app.servlets;

import com.app.dto.*;
import com.app.entity.*;
import com.app.exceptions.ValidationException;
import com.app.service.AdminService;
import com.app.validator.Error;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final AdminService adminService = AdminService.getInstance();

    private final List<String> employeeAddStatus = new ArrayList<>();
    private final List<String> searchTables = new ArrayList<>();

    {
        employeeAddStatus.add("addEmployee");
        employeeAddStatus.add("addEmployeePassport");
        employeeAddStatus.add("addEmployeeMedicalCard");
        employeeAddStatus.add("addEmployeeEmploymentCard");
        employeeAddStatus.add("addEmployeeEducationCard");

        searchTables.add("availableRoutes");
        searchTables.add("cargos");
        searchTables.add("freighters");
        searchTables.add("ships");
        searchTables.add("teams");
        searchTables.add("clients");
        searchTables.add("orders");
        searchTables.add("workers");
        searchTables.add("positions");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/admin-page.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        var currentStatus = (String) session.getAttribute("active");
        if (employeeAddStatus.contains(currentStatus)) {
            switch (currentStatus) {
                case "addEmployee": {

                    session.setAttribute("tempFullName", req.getParameter("employeeFullName"));
                    var position = adminService.findByPositionName(req.getParameter("employeePosition")).get();
                    session.setAttribute("position", position);
                    session.setAttribute("active", employeeAddStatus.get(employeeAddStatus.indexOf(currentStatus) + 1));
                    resp.sendRedirect("/admin");
                    break;
                }
                case "addEmployeePassport": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {//Кнопка крестик убирает актив
                        session.setAttribute("active", "");
                        doGet(req, resp);
                        break;
                    }
                    try { // Валидация
                        var passport = CreatePassportDto.builder()
                                .birthDate(LocalDate.parse(req.getParameter("birthDate")))
                                .sex(req.getParameter("sex"))
                                .fullName((String) session.getAttribute("tempFullName"))
                                .passportSerialNumber(req.getParameter("serialNumber"))
                                .citizenship(req.getParameter("citizenship"))
                                .build();
                        adminService.checkPassport(passport);
                        session.setAttribute("passport", passport);
                    } catch (ValidationException e) {
                        req.setAttribute("errors", e.getErrors());
                        doGet(req, resp);
                        break;
                    }
                    try {
                        var registration = CreateRegistrationDto.builder()
                                .street(req.getParameter("street"))
                                .region(req.getParameter("region"))
                                .house(req.getParameter("house"))
                                .city(req.getParameter("city"))
                                .flat(Integer.parseInt(req.getParameter("flat")))
                                .build();
                        adminService.checkRegistration(registration);
                        session.setAttribute("registration", registration);
                    } catch (ValidationException e) {
                        req.setAttribute("errors", e.getErrors());
                        doGet(req, resp);
                        break;
                    }

                    session.setAttribute("active", employeeAddStatus.get(employeeAddStatus.indexOf(currentStatus) + 1));
                    session.removeAttribute("tempFullName");
                    resp.sendRedirect("/admin");
                    break;
                }

                case "addEmployeeMedicalCard": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        doGet(req, resp);
                        break;
                    }

                    try {
                        var medicalCard = CreateMedicalCardDto.builder()
                                .medSerialNumber(req.getParameter("medSerialNumber"))
                                .illness(req.getParameter("illness"))
                                .hivStatus(Boolean.valueOf(req.getParameter("hivStatus")))
                                .build();
                        adminService.checkMedicalCard(medicalCard);
                        session.setAttribute("medicalCard", medicalCard);
                    } catch (ValidationException e) {
                        req.setAttribute("errors", e.getErrors());
                        doGet(req, resp);
                        break;
                    }

                    session.setAttribute("active", employeeAddStatus.get(employeeAddStatus.indexOf(currentStatus) + 1));
                    resp.sendRedirect("/admin");
                    break;
                }

                case "addEmployeeEmploymentCard": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        resp.sendRedirect("/admin");
                        break;
                    }

                    try {
                        var employmentCard = CreateEmploymentDto.builder()
                                .previousJob(req.getParameter("previousJob"))
                                .experience(Integer.parseInt(req.getParameter("experience")))
                                .employmentSerialNumber(req.getParameter("employmentSerialNumber"))
                                .build();
                        adminService.checkEmploymentCard(employmentCard);
                        session.setAttribute("employmentCard", employmentCard);
                    } catch (ValidationException e) {
                        req.setAttribute("errors", e.getErrors());
                        doGet(req, resp);
                        break;
                    }

                    session.setAttribute("active", employeeAddStatus.get(employeeAddStatus.indexOf(currentStatus) + 1));
                    resp.sendRedirect("/admin");
                    break;
                }
                case "addEmployeeEducationCard": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        doGet(req, resp);
                        break;
                    }

                    try {
                        var education = CreateEducationDto.builder()
                                .educationSerialNumber(req.getParameter("educationSerialNumber"))
                                .establishment(req.getParameter("educationSerialNumber"))
                                .grade(req.getParameter("grade"))
                                .build();

                        adminService.checkEducationCard(education);
                        session.setAttribute("active", " ");

                        var passport = (CreatePassportDto) session.getAttribute("passport");
                        var medicalCard = (CreateMedicalCardDto) session.getAttribute("medicalCard");
                        var registration = (CreateRegistrationDto) session.getAttribute("registration");
                        var employmentCard = (CreateEmploymentDto) session.getAttribute("employmentCard");
                        var position = (Position) session.getAttribute("position");
                        adminService.saveWorker(education, employmentCard, passport, registration, medicalCard, position);
                    } catch (ValidationException e) {
                        req.setAttribute("errors", e.getErrors());
                        doGet(req, resp);
                        break;
                    }

                    resp.sendRedirect("/admin");
                    break;
                }
            }

        } else {
            switch (currentStatus) {
                case "addFreighter": {
                    Freighter freighter = Freighter.builder()
                            .freighterName(req.getParameter("freighterName"))
                            .fragileCost(Integer.parseInt(req.getParameter("fragileCost")))
                            .tax(Integer.parseInt(req.getParameter("tax")))
                            .weightCost(Integer.parseInt(req.getParameter("weightCost")))
                            .sizeCost(Integer.parseInt(req.getParameter("sizeCost")))
                            .build();
                    adminService.saveFreighter(freighter);
                    session.setAttribute("active", " ");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "addAvailableRoute": {
                    try {
                        Route route = Route.builder()
                                .destinationCountry(req.getParameter("routeDirectionCountry"))
                                .destinationCity(req.getParameter("routeDirectionCity"))
                                .duration(Integer.parseInt(req.getParameter("duration")))
                                .build();
                        adminService.checkRoute(route);
                        var freighters = adminService.getFreighters();
                        List<Freighter> freightersResult = freighters.stream()
                                .filter(value -> req.getParameter(value.getFreighterName()) != null)
                                .toList();

                        adminService.saveRoute(route, freightersResult);
                        session.setAttribute("active", "");
                    } catch (ValidationException e) {
                        req.setAttribute("errors", e.getErrors());
                        doGet(req, resp);
                        break;
                    }
                    resp.sendRedirect("/admin");
                    break;
                }
                case "deleteWorker":{
                    try {
                    String passport = req.getParameter("passport");
                    adminService.checkPassport(passport);
                    adminService.deleteWorker(passport);
                    session.setAttribute("active","");
                    }
                    catch (ValidationException e){
                        req.setAttribute("errors", e.getErrors());
                        doGet(req, resp);
                        break;
                    }
                    resp.sendRedirect("/admin");
                    break;
                }
                case "showSearch": {
                    String selectedTable = req.getParameter("selectedTable");
                    session.setAttribute("active", selectedTable);
                    for (String searchTable : searchTables) {
                        if (searchTable.equals("availableRoutes")) {
                            session.setAttribute("freighters", adminService.getFreighters());
                            List<String> countries = adminService.getAvailableRoutes().stream()
                                    .map(Route::getDestinationCountry)
                                    .distinct()
                                    .toList();
                            session.setAttribute("availableRoutesCountries", countries);
                            session.setAttribute("availableRoutes", adminService.getAvailableRoutes());
                        }
                        if (searchTable.equals("cargos")) {
                            List<String> countries = adminService.getAvailableRoutes().stream()
                                    .map(Route::getDestinationCountry)
                                    .distinct()
                                    .toList();
                            session.setAttribute("availableRoutesCountries", countries);
                            session.setAttribute("freighters", adminService.getFreighters());


                        }
                        if (searchTable.equals("freighters")) {
                            session.setAttribute("freighters", adminService.getFreighters());
                        }
                        if (searchTable.equals("ships")) {
                            session.setAttribute("freighters", adminService.getFreighters());
                            session.setAttribute("ships", adminService.getAllShips());
                        }
                        if (searchTable.equals("teams")) {
                            session.setAttribute("teamMembers", adminService.getAllTeamMembers().stream().map(TeamMember::getCitizenship).distinct().toList());
                        }
                        if (searchTable.equals("orders")) {
                            session.setAttribute("statusList", adminService.getAllOrders().stream().map(Order::getStatus).distinct().toList());
                        }
                        if (searchTable.equals("workers")) {
                            session.setAttribute("positionList", adminService.getAllPositions().stream().map(Position::getPosition).toList());
                        }
                    }

                    resp.sendRedirect("/admin");
                    break;
                }
                case "availableRoutes": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        resp.sendRedirect("/admin");
                        break;
                    }

                    var freighters = adminService.getFreighters(); // Какие перевозчики выбраны
                    List<Freighter> freightersResult = freighters.stream()
                            .filter(value -> req.getParameter(value.getFreighterName()) != null)
                            .toList();
                    if (freightersResult.isEmpty()) {
                        freightersResult = freighters;
                    }

                    var countries = adminService.getAvailableRoutes(); // Какие страны выбраны
                    List<Route> countriesResult = countries.stream()
                            .filter(value -> req.getParameter(value.getDestinationCountry()) != null)
                            .toList();
                    if (countriesResult.isEmpty()) {
                        countriesResult = countries;
                    }

                    List<Route> routesResult = countriesResult.stream() // Какие города выбраны
                            .filter(value -> req.getParameter(value.getDestinationCity()) != null)
                            .toList();
                    if (routesResult.isEmpty()) {
                        routesResult = countriesResult;
                    }
                    Integer durationFrom = 0;
                    Integer durationTo = 9999;
                    if (!req.getParameter("durationFrom").isBlank()) {
                        durationFrom = Integer.parseInt(req.getParameter("durationFrom"));
                    }
                    if (!req.getParameter("durationTo").isBlank()) {
                        durationTo = Integer.parseInt(req.getParameter("durationTo"));
                    }


                    if (durationFrom > durationTo) {
                        req.setAttribute("error", Error.of("invalid.interval.jsp","Неправильно указан промежуток от > до"));
                        doGet(req,resp);
                        break;
                    }

                    List<FreighterRoutes> filteredRoutes = adminService.getFilteredRoutes(freightersResult, routesResult, durationFrom, durationTo);
                    session.setAttribute("searchResult", filteredRoutes);
                    session.setAttribute("active", "showSearchResultAvailableRoutes");
                    resp.sendRedirect("/admin");

                    break;
                }
                case "cargos": {

                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        resp.sendRedirect("/admin");
                        break;
                    }
                    Integer weightFrom = 0;
                    Integer weightTo = 19999;

                    Boolean isFragile = null;
                    Boolean allCargos = null;

                    Integer customerIdTemp;
                    Integer orderIdTemp;
                    Integer sizeFrom = 0;
                    Integer sizeTo = 19999;


                    if (!req.getParameter("weightFrom").isBlank()) {
                        weightFrom = Integer.parseInt(req.getParameter("weightFrom"));
                    }
                    if (!req.getParameter("weightTo").isBlank()) {
                        weightTo = Integer.parseInt(req.getParameter("weightTo"));
                    }

                    if (!req.getParameter("sizeFrom").isBlank()) {
                        sizeFrom = Integer.parseInt(req.getParameter("sizeFrom"));
                    }
                    if (!req.getParameter("sizeTo").isBlank()) {
                        sizeTo = Integer.parseInt(req.getParameter("sizeTo"));
                    }


                    isFragile = req.getParameter("fragile") != null;

                    allCargos = req.getParameter("allCargos") != null;
                    if (!req.getParameter("clientId").isBlank()) {
                        customerIdTemp = Integer.parseInt(req.getParameter("clientId"));
                    } else {
                        customerIdTemp = null;
                    }
                    if (!req.getParameter("orderId").isBlank()) {
                        orderIdTemp = Integer.parseInt(req.getParameter("orderId"));
                    } else {
                        orderIdTemp = null;
                    }


                    if ((weightFrom > weightTo) || (sizeFrom > sizeTo)) {
                        req.setAttribute("error", Error.of("invalid.interval.jsp","Неправильно указан промежуток от > до"));
                        doGet(req,resp);
                        break;
                    }

                    var countries = adminService.getAvailableRoutes(); // Какие страны выбраны
                    List<String> countriesResult = countries.stream()
                            .map(Route::getDestinationCountry)
                            .filter(destinationCountry -> req.getParameter(destinationCountry) != null)
                            .toList();
                    if (countriesResult.isEmpty()) {
                        countriesResult = countries.stream().map(Route::getDestinationCountry).toList();
                    }
                    var clients = adminService.getAllClients();
                    List<Integer> clientsResult;
                    if (customerIdTemp == null) {
                        clientsResult = clients.stream()
                                .map(Customer::getCustomerId)
                                .filter(customerId -> customerId.equals(customerIdTemp))
                                .toList();
                        if (clientsResult.isEmpty()) {
                            clientsResult = clients.stream().map(Customer::getCustomerId).toList();
                        }
                    } else {
                        clientsResult = clients.stream()
                                .map(Customer::getCustomerId)
                                .filter(customerId -> customerId.equals(customerIdTemp))
                                .toList();
                    }
                    List<Integer> ordersResult;
                    var orders = adminService.getAllOrders();
                    if (orderIdTemp == null) {
                        ordersResult = orders.stream()
                                .map(Order::getOrderId)
                                .filter(orderId -> orderId.equals(orderIdTemp))
                                .toList();
                        if (ordersResult.isEmpty()) {
                            ordersResult = orders.stream().map(Order::getOrderId).toList();
                        }
                    } else {
                        ordersResult = orders.stream()
                                .map(Order::getOrderId)
                                .filter(orderId -> orderId.equals(orderIdTemp))
                                .toList();
                    }
                    var freighters = adminService.getFreighters(); // Какие перевозчики выбраны
                    List<String> freightersResult = freighters.stream()
                            .map(Freighter::getFreighterName)
                            .filter(freighterName -> req.getParameter(freighterName) != null)
                            .toList();
                    if (freightersResult.isEmpty()) {
                        freightersResult = freighters.stream().map(Freighter::getFreighterName).toList();
                    }

                    List<Cargo> filteredCargos = adminService.getFilteredCargos(weightFrom, weightTo, sizeFrom, sizeTo, isFragile,allCargos
                            ,clientsResult, countriesResult, freightersResult,ordersResult);
                    session.setAttribute("searchResult", filteredCargos);
                    session.setAttribute("active", "showSearchResultCargos");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "freighters": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        resp.sendRedirect("/admin");
                        break;
                    }
                    Integer taxFrom = 0;
                    Integer taxTo = 1000;

                    Integer weightCostFrom = 0;
                    Integer weightCostTo = 10000000;

                    Integer sizeCostFrom = 0;
                    Integer sizeCostTo = 10000000;

                    Integer fragileCostFrom = 0;
                    Integer fragileCostTo = 1000000;

                    var freighters = adminService.getFreighters();

                    if (!req.getParameter("taxFrom").isBlank()) {
                        taxFrom = Integer.parseInt(req.getParameter("taxFrom"));
                    }
                    if (!req.getParameter("taxTo").isBlank()) {
                        taxTo = Integer.parseInt(req.getParameter("taxTo"));
                    }
                    if (!req.getParameter("weightCostFrom").isBlank()) {
                        weightCostFrom = Integer.parseInt(req.getParameter("weightCostFrom"));
                    }
                    if (!req.getParameter("weightCostTo").isBlank()) {
                        weightCostTo = Integer.parseInt(req.getParameter("weightCostTo"));
                    }
                    if (!req.getParameter("sizeCostFrom").isBlank()) {
                        sizeCostFrom = Integer.parseInt(req.getParameter("sizeCostFrom"));
                    }
                    if (!req.getParameter("sizeCostTo").isBlank()) {
                        sizeCostTo = Integer.parseInt(req.getParameter("sizeCostTo"));
                    }
                    if (!req.getParameter("fragileCostFrom").isBlank()) {
                        fragileCostFrom = Integer.parseInt(req.getParameter("fragileCostFrom"));
                    }
                    if (!req.getParameter("fragileCostTo").isBlank()) {
                        fragileCostTo = Integer.parseInt(req.getParameter("fragileCostTo"));
                    }
                    if ((weightCostFrom > weightCostTo) || (sizeCostFrom > sizeCostTo) || (taxFrom > taxTo) || (fragileCostFrom > fragileCostTo)) {
                        req.setAttribute("error", Error.of("invalid.interval.jsp","Неправильно указан промежуток от > до"));
                        doGet(req,resp);
                        break;
                    }

                    var freightersResult = freighters.stream().filter(value -> req.getParameter(value.getFreighterName()) != null)
                            .toList();
                    if (freightersResult.isEmpty()) {
                        freightersResult = freighters;
                    }

                    List<Freighter> filteredFreighters = adminService.getFilteredFreighters(weightCostFrom, weightCostTo, sizeCostFrom, sizeCostTo, taxFrom, taxTo, fragileCostFrom, fragileCostTo, freightersResult);

                    session.setAttribute("searchResult", filteredFreighters);
                    session.setAttribute("active", "showSearchResultFreighters");
                    resp.sendRedirect("/admin");


                    break;
                }
                case "ships": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        resp.sendRedirect("/admin");
                        break;
                    }
                    Integer shipSizeFrom = 0;
                    Integer shipSizeTo = 10000000;

                    Integer teamId;

                    Integer shipCapacityFrom = 0;
                    Integer shipCapacityTo = 10000000;
                    Boolean allShips = false;
                    Boolean inUse = false;
                    var freighters = adminService.getFreighters();
                    var ships = adminService.getAllShips();
                    var teams = adminService.getAllShips().stream().map(Ship::getTeam).toList();


                    if (!req.getParameter("teamId").isBlank()) {
                        teamId = Integer.parseInt(req.getParameter("teamId"));
                    } else {
                        teamId = null;
                    }
                    if (!req.getParameter("shipSizeFrom").isBlank()) {
                        shipSizeFrom = Integer.parseInt(req.getParameter("shipSizeFrom"));
                    }
                    if (!req.getParameter("shipSizeTo").isBlank()) {
                        shipSizeTo = Integer.parseInt(req.getParameter("shipSizeTo"));
                    }
                    if (!req.getParameter("shipCapacityFrom").isBlank()) {
                        shipCapacityFrom = Integer.parseInt(req.getParameter("shipCapacityFrom"));
                    }
                    if (!req.getParameter("shipCapacityTo").isBlank()) {
                        shipCapacityTo = Integer.parseInt(req.getParameter("shipCapacityTo"));
                    }
                    if (req.getParameter("allShips") == null) {
                        if (req.getParameter("inUse") != null) {
                            inUse = true;
                        } else {
                            inUse = false;
                        }
                    } else allShips = true;

                    if ((shipSizeFrom > shipSizeTo) || (shipCapacityFrom > shipCapacityTo)) {
                        req.setAttribute("error", Error.of("invalid.interval.jsp","Неправильно указан промежуток от > до"));
                        doGet(req,resp);
                        break;
                    }
                    var freightersResult = freighters.stream().filter(value -> req.getParameter(value.getFreighterName()) != null)
                            .toList();
                    if (freightersResult.isEmpty()) {
                        freightersResult = freighters;
                    }

                    var shipsResult = ships.stream().filter(value -> req.getParameter(value.getShipModel().getShipModel()) != null)
                            .toList();
                    if (shipsResult.isEmpty()) {
                        shipsResult = ships;
                    }
                    List<Team> teamsResult;
                    if (teamId == null) {
                        teamsResult = teams.stream().filter(value -> value.getTeamId().equals(teamId))
                                .toList();
                        if (teamsResult.isEmpty()) {
                            teamsResult = teams;
                        }
                    } else {
                        teamsResult = teams.stream().filter(value -> value.getTeamId().equals(teamId))
                                .toList();
                    }


                    List<Ship> filteredShips = adminService.getFilteredShips(shipSizeFrom, shipSizeTo, shipCapacityFrom, shipCapacityTo, allShips, inUse, freightersResult, shipsResult, teamsResult);

                    session.setAttribute("searchResult", filteredShips);
                    session.setAttribute("active", "showSearchResultShips");
                    resp.sendRedirect("/admin");


                    break;
                }
                case "teams": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        resp.sendRedirect("/admin");
                        break;
                    }
                    String fullName;
                    var citizenships = adminService.getAllTeamMembers().stream().map(TeamMember::getCitizenship).toList();
                    var fullNames = adminService.getAllTeamMembers().stream().map(TeamMember::getFullName).toList();
                    Integer experienceFrom = 0;
                    Integer experienceTo = 1000;

                    if (!req.getParameter("experienceFrom").isBlank()) {
                        experienceFrom = Integer.parseInt(req.getParameter("experienceFrom"));
                    }
                    if (!req.getParameter("experienceTo").isBlank()) {
                        experienceTo = Integer.parseInt(req.getParameter("experienceTo"));
                    }
                    if ((experienceFrom > experienceTo)) {
                        req.setAttribute("error", Error.of("invalid.interval.jsp","Неправильно указан промежуток от > до"));
                        doGet(req,resp);
                        break;
                    }
                    if (!req.getParameter("memberFullName").isBlank()) {
                        fullName = req.getParameter("memberFullName");
                    } else {
                        fullName = "";
                    }
                    var citizenshipsResult = citizenships.stream().filter(value -> req.getParameter(value) != null)
                            .toList();
                    if (citizenshipsResult.isEmpty()) {
                        citizenshipsResult = citizenships;
                    }
                    List<String> fullNamesResult;
                    if (fullName.isBlank()) {
                        fullNamesResult = fullNames.stream().filter(value -> value.equals(fullName))
                                .toList();
                        if (fullNamesResult.isEmpty()) {
                            fullNamesResult = fullNames;
                        }
                    } else {
                        fullNamesResult = fullNames.stream().filter(value -> value.equals(fullName))
                                .toList();
                    }
                    List<Team> filteredTeams = adminService.getFilteredTeams(experienceFrom, experienceTo, fullNamesResult, citizenshipsResult);

                    session.setAttribute("searchResult", filteredTeams);
                    session.setAttribute("active", "showSearchResultTeams");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "clients": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        resp.sendRedirect("/admin");
                        break;
                    }
                    String customerFullName;
                    String customerEmail;
                    String customerLogin;

                    if (!req.getParameter("customerFullName").isBlank()) {
                        customerFullName = req.getParameter("customerFullName");
                    } else customerFullName = "";

                    if (!req.getParameter("customerEmail").isBlank()) {
                        customerEmail = req.getParameter("customerEmail");
                    } else customerEmail = "";

                    if (!req.getParameter("customerLogin").isBlank()) {
                        customerLogin = req.getParameter("customerLogin");
                    } else customerLogin = "";

                    List<Customer> filteredCustomers = adminService.getFilteredCustomers(customerFullName, customerEmail, customerLogin);


                    session.setAttribute("searchResult", filteredCustomers);
                    session.setAttribute("active", "showSearchResultClients");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "orders": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        resp.sendRedirect("/admin");
                        break;
                    }
                    Integer clientId;
                    Integer orderId;
                    String fullName;

                    LocalDateTime orderDateFrom;
                    LocalDateTime orderDateTo;

                    var statusList = adminService.getAllOrders().stream().map(Order::getStatus).distinct().toList();

                    var temp = req.getParameter("orderClientId");

                    if (!req.getParameter("orderClientId").isBlank()) {
                        clientId = Integer.parseInt(req.getParameter("orderClientId"));
                    } else clientId = null;
                    if (!req.getParameter("orderId").isBlank()) {
                        orderId = Integer.parseInt(req.getParameter("orderId"));
                    } else orderId = null;
                    if (!req.getParameter("orderCustomerFullName").isBlank()) {
                        fullName = req.getParameter("orderCustomerFullName");
                    } else fullName = "";

                    if (!req.getParameter("orderDateFrom").isBlank()) {
                        orderDateFrom = LocalDateTime.parse(req.getParameter("orderDateFrom"));
                    } else orderDateFrom = LocalDateTime.parse("1930-01-01T00:00:00");

                    if (!req.getParameter("orderDateTo").isBlank()) {
                        orderDateTo = LocalDateTime.parse(req.getParameter("orderDateTo"));
                    } else orderDateTo = LocalDateTime.parse("2024-01-01T00:00:00");

                    if (orderDateFrom.compareTo(orderDateTo) > 0) {
                        req.setAttribute("error", Error.of("invalid.interval.jsp","Неправильно указан промежуток от > до"));
                        doGet(req,resp);
                        break;
                    }

                    var statusListResult = statusList.stream().filter(value -> req.getParameter(value) != null)
                            .toList();
                    if (statusListResult.isEmpty()) {
                        statusListResult = statusList;
                    }

                    var filteredOrders = adminService.getFilteredOrders(clientId, orderId, fullName, orderDateFrom, orderDateTo, statusListResult);
                    List<Cargo> filteredOrdersResult = new ArrayList<>();
                    for (List<Cargo> value : filteredOrders.values()) {
                        filteredOrdersResult.add(value.get(0));
                    }

                    session.setAttribute("searchResult", filteredOrdersResult);
                    session.setAttribute("active", "showSearchResultOrders");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "workers": {
                    if (Boolean.valueOf(req.getParameter("exit")).equals(true)) {
                        session.setAttribute("active", "");
                        resp.sendRedirect("/admin");
                        break;
                    }
                    Integer dockId;
                    Integer ageFrom;
                    Integer ageTo;
                    Integer salaryFrom;
                    Integer salaryTo;
                    String passportSerialNumber;
                    String fullName;
                    LocalDate hiringDateFrom;
                    LocalDate hiringDateTo;
                    var positionList = adminService.getAllPositions();

                    if (!req.getParameter("dockId").isBlank()) {
                        dockId = Integer.parseInt(req.getParameter("dockId"));
                    } else dockId = null;

                    if (!req.getParameter("ageFrom").isBlank()) {
                        ageFrom = Integer.parseInt(req.getParameter("ageFrom"));
                    } else ageFrom = 0;

                    if (!req.getParameter("ageTo").isBlank()) {
                        ageTo = Integer.parseInt(req.getParameter("ageTo"));
                    } else ageTo = 200;

                    if (!req.getParameter("salaryFrom").isBlank()) {
                        salaryFrom = Integer.parseInt(req.getParameter("salaryFrom"));
                    } else salaryFrom = 0;

                    if (!req.getParameter("salaryTo").isBlank()) {
                        salaryTo = Integer.parseInt(req.getParameter("salaryTo"));
                    } else salaryTo = 10000000;

                    if (!req.getParameter("passportSerialNumber").isBlank()) {
                        passportSerialNumber = req.getParameter("passportSerialNumber");
                    } else passportSerialNumber = "";

                    if (!req.getParameter("workerFullName").isBlank()) {
                        fullName = req.getParameter("workerFullName");
                    } else fullName = "";

                    if (!req.getParameter("hiringDateFrom").isBlank()) {
                        hiringDateFrom = LocalDate.parse(req.getParameter("hiringDateFrom"));
                    } else hiringDateFrom = LocalDate.parse("1930-01-01");

                    if (!req.getParameter("hiringDateTo").isBlank()) {
                        hiringDateTo = LocalDate.parse(req.getParameter("hiringDateTo"));
                    } else hiringDateTo = LocalDate.parse("2024-01-01");

                    if ((hiringDateFrom.isAfter(hiringDateTo)) || (ageFrom > ageTo) || (salaryFrom > salaryTo)) {
                        req.setAttribute("error", Error.of("invalid.interval.jsp","Неправильно указан промежуток от > до"));
                        doGet(req,resp);
                        break;
                    }

                    var positionListResult = positionList.stream().filter(value -> req.getParameter(value.getPosition()) != null).toList();
                    if (positionListResult.isEmpty()) {
                        positionListResult = positionList;
                    }
                    List<Worker> filteredWorkers = adminService.getFilteredWorkers(dockId, ageFrom, ageTo, salaryFrom, salaryTo, passportSerialNumber, fullName, hiringDateFrom, hiringDateTo, positionListResult);

                    session.setAttribute("searchResult", filteredWorkers);
                    session.setAttribute("active", "showSearchResultWorkers");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "sortAvailableRoutes":{
                    var list = (List<FreighterRoutes>) session.getAttribute("searchResult");

                    String sortBy = req.getParameter("sortBy");

                    session.setAttribute("searchResult",adminService.sortRoutesBy(sortBy,list));
                    session.setAttribute("active","showSearchResultAvailableRoutes");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "sortCargos":{
                    var list = (List<Cargo>) session.getAttribute("searchResult");

                    String sortBy = req.getParameter("sortBy");

                    session.setAttribute("searchResult",adminService.sortCargosBy(sortBy,list));
                    session.setAttribute("active","showSearchResultCargos");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "sortFreighters":{
                    var list = (List<Freighter>) session.getAttribute("searchResult");

                    String sortBy = req.getParameter("sortBy");

                    session.setAttribute("searchResult",adminService.sortFreightersBy(sortBy,list));
                    session.setAttribute("active","showFreighters");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "sortShips":{
                    var list = (List<Ship>) session.getAttribute("searchResult");

                    String sortBy = req.getParameter("sortBy");

                    session.setAttribute("searchResult",adminService.sortShipsBy(sortBy,list));
                    session.setAttribute("active","showSearchResultShips");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "sortTeams":{
                    var list = (List<Team>) session.getAttribute("searchResult");

                    String sortBy = req.getParameter("sortBy");

                    session.setAttribute("searchResult",adminService.sortTeamsBy(sortBy,list));
                    session.setAttribute("active","showSearchResultTeams");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "sortClients":{
                    var list = (List<Customer>) session.getAttribute("searchResult");

                    String sortBy = req.getParameter("sortBy");

                    session.setAttribute("searchResult",adminService.sortClientsBy(sortBy,list));
                    session.setAttribute("active","showCustomers");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "sortOrders":{
                    var list = (List<Cargo>) session.getAttribute("searchResult");

                    String sortBy = req.getParameter("sortBy");

                    session.setAttribute("searchResult",adminService.sortOrdersBy(sortBy,list));
                    session.setAttribute("active","showSearchResultOrders");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "sortWorkers":{
                    var list = (List<Worker>) session.getAttribute("searchResult");

                    String sortBy = req.getParameter("sortBy");

                    session.setAttribute("searchResult",adminService.sortWorkersBy(sortBy,list));
                    session.setAttribute("active","showWorkers");
                    resp.sendRedirect("/admin");
                    break;
                }
                case "sortLog":{
                    var list = (List<VoyageLog>) session.getAttribute("voyageLog");

                    String sortBy = req.getParameter("sortBy");
                    session.setAttribute("voyageLog",adminService.sortLogBy(sortBy,list));

                    session.setAttribute("active","showVoyageLog");
                    resp.sendRedirect("/admin");
                    break;
                }

            }

        }


    }
}
