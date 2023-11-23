package com.app.servlets;

import com.app.dto.*;
import com.app.entity.Position;
import com.app.service.AdminService;
import com.app.service.LoginService;
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
        employeeAddStatus.add("addEmployeeEducationCard");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/admin-page.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        var currentStatus = (String)session.getAttribute("active");
        if (employeeAddStatus.contains(currentStatus)){
            switch (currentStatus) {
                case "addEmployee":{
                    session.setAttribute("tempFullName",req.getParameter("employeeFullName"));
                    var position = adminService.findByPositionName(req.getParameter("employeePosition")).get() ;
                    session.setAttribute("position",position);
                    session.setAttribute("active",employeeAddStatus.get(employeeAddStatus.indexOf(currentStatus) + 1));
                    req.getRequestDispatcher("/WEB-INF/jsp/admin-page.jsp")
                            .forward(req,resp);
                    break;
                }
                case "addEmployeePassport":{
                    var passport = CreatePassportDto.builder()
                            .birthDate(LocalDate.parse(req.getParameter("birthDate")))
                            .sex(req.getParameter("sex"))
                            .fullName((String) session.getAttribute("tempFullName"))
                            .passportSerialNumber(req.getParameter("serialNumber"))
                            .citizenship(req.getParameter("citizenship"))
                            .build();
                    var registration = CreateRegistrationDto.builder()
                            .street(req.getParameter("street"))
                            .region(req.getParameter("region"))
                            .house(req.getParameter("house"))
                            .city(req.getParameter("city"))
                            .flat(Integer.parseInt(req.getParameter("flat")))
                            .build();
                    session.setAttribute("active",employeeAddStatus.get(employeeAddStatus.indexOf(currentStatus) + 1));
                    session.setAttribute("passport",passport);
                    session.setAttribute("registration",registration);
                    session.removeAttribute("tempFullName");
                    req.getRequestDispatcher("/WEB-INF/jsp/admin-page.jsp")
                            .forward(req,resp);
                    break;
                }

                case "addEmployeeMedicalCard":{
                    var medicalCard = CreateMedicalCardDto.builder()
                            .medSerialNumber(req.getParameter("medSerialNumber"))
                            .illness(req.getParameter("illness"))
                            .hivStatus(Boolean.valueOf(req.getParameter("hivStatus")))
                            .build();

                    session.setAttribute("active",employeeAddStatus.get(employeeAddStatus.indexOf(currentStatus) + 1));
                    session.setAttribute("medicalCard",medicalCard);
                    req.getRequestDispatcher("/WEB-INF/jsp/admin-page.jsp")
                            .forward(req,resp);
                    break;
                }

                case "addEmployeeEmploymentCard":{
                    var employmentCard = CreateEmploymentDto.builder()
                            .previousJob(req.getParameter("previousJob"))
                            .experience(Integer.parseInt(req.getParameter("experience")))
                            .employmentSerialNumber(req.getParameter("employmentSerialNumber"))
                            .build();
                    session.setAttribute("active",employeeAddStatus.get(employeeAddStatus.indexOf(currentStatus) + 1));
                    session.setAttribute("employmentCard",employmentCard);
                    req.getRequestDispatcher("/WEB-INF/jsp/admin-page.jsp")
                            .forward(req,resp);
                    break;
                }
                case "addEmployeeEducationCard": {
                var education = CreateEducationDto.builder()
                        .educationSerialNumber(req.getParameter("educationSerialNumber"))
                        .establishment(req.getParameter("educationSerialNumber"))
                        .grade(req.getParameter("grade"))
                        .build();
                    session.setAttribute("active"," ");

                    var passport = (CreatePassportDto)session.getAttribute("passport");
                    var medicalCard = (CreateMedicalCardDto)session.getAttribute("medicalCard");
                    var registration = (CreateRegistrationDto) session.getAttribute("registration");
                    var employmentCard = (CreateEmploymentDto) session.getAttribute("employmentCard");
                    var position = (Position)session.getAttribute("position");
                    adminService.saveWorker(education,employmentCard,passport,registration,medicalCard,position);

                    req.getRequestDispatcher("/WEB-INF/jsp/admin-page.jsp")
                            .forward(req,resp);
                    break;
                }
            }

        }

    }
}
