package com.app.servlets;

import com.app.dto.*;
import com.app.entity.Freighter;
import com.app.entity.Position;
import com.app.exceptions.ValidationException;
import com.app.service.AdminService;
import com.app.service.LoginService;
import com.app.validator.Error;
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

    {
        employeeAddStatus.add("addEmployee");
        employeeAddStatus.add("addEmployeePassport");
        employeeAddStatus.add("addEmployeeMedicalCard");
        employeeAddStatus.add("addEmployeeEmploymentCard");
        employeeAddStatus.add("addEmployeeEducationCard");
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
                        session.setAttribute("active","");
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
                        session.setAttribute("active","");
                        doGet(req,resp);
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
                    }
                    catch (ValidationException e){
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
                        session.setAttribute("active","");
                        doGet(req,resp);
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
                    }
                    catch (ValidationException e){
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
                        session.setAttribute("active","");
                        doGet(req,resp);
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
                        doGet(req,resp);
                    }
                    catch (ValidationException e){
                        req.setAttribute("errors", e.getErrors());
                        doGet(req, resp);
                        break;
                    }

                    resp.sendRedirect("/admin");
                    break;
                }
            }

        }
        if (currentStatus.equals("addFreighter")) {

            Freighter freighter = Freighter.builder()
                    .freighterName(req.getParameter("freighterName"))
                    .fragileCost(Integer.parseInt(req.getParameter("fragileCost")))
                    .tax(Integer.parseInt(req.getParameter("tax")))
                    .weightCost(Integer.parseInt(req.getParameter("weightCost")))
                    .sizeCost(Integer.parseInt(req.getParameter("sizeCost")))
                    .build();

            adminService.saveFreighter(freighter);
            session.setAttribute("active", " ");
            doGet(req,resp);

        }


    }
}
