package com.app.servlets;

import com.app.dto.*;
import com.app.entity.*;
import com.app.exceptions.ValidationException;
import com.app.service.AdminService;
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
                        doGet(req, resp);
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
                    }

                    resp.sendRedirect("/admin");
                    break;
                }
                case "availableRoutes": {
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
                        session.setAttribute("error", "Неправильно выбран промежуток( от > до )");
                        doGet(req, resp);
                        break;
                    }

                    List<FreighterRoutes> filteredRoutes = adminService.getFilteredRoutes(freightersResult, routesResult, durationFrom, durationTo);
                    session.setAttribute("searchResult", filteredRoutes);
                    session.setAttribute("active", "showSearchResultAvailableRoutes");
                    resp.sendRedirect("/admin");

                    break;
                }
                case "cargos": {
                    Integer weightFrom = 0;
                    Integer weightTo = 19999;

                    Boolean isFragile = null;

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


//                    if (!req.getParameter("fragile").isBlank()) {
//                        isFragile = Boolean.valueOf(req.getParameter("fragile"));
//                    }



                    if ((weightFrom > weightTo)|| (sizeFrom > sizeTo)) {
                        session.setAttribute("error", "Неправильно выбран промежуток( от > до )");
                        doGet(req, resp);
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
                    List<Integer> clientsResult = clients.stream()
                            .map(Customer::getCustomerId)
                            .filter(customerId -> req.getParameter(String.valueOf(customerId)) != null)
                            .toList();
                    if (clientsResult.isEmpty()){
                        clientsResult = clients.stream().map(Customer::getCustomerId).toList();
                    }
                    var freighters = adminService.getFreighters(); // Какие перевозчики выбраны
                    List<String> freightersResult = freighters.stream()
                            .map(Freighter::getFreighterName)
                            .filter(freighterName -> req.getParameter(freighterName) != null)
                            .toList();
                    if (freightersResult.isEmpty()){
                        freightersResult = freighters.stream().map(Freighter::getFreighterName).toList();
                    }

                    List<Cargo> filteredCargos = adminService.getFilteredCargos(weightFrom, weightTo, sizeFrom, sizeTo, isFragile, clientsResult, countriesResult, freightersResult);
                    session.setAttribute("searchResult", filteredCargos);
                    session.setAttribute("active","showSearchResultCargos");
                    resp.sendRedirect("/admin");
                    break;
                }
            }

        }


    }
}
