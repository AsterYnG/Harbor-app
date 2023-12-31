package com.app.servlets;

import com.app.entity.Customer;
import com.app.service.RegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildCustomer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final RegistrationService regService = RegistrationService.getInstance();
//1 узнать как передавать с сайта
    // 2 обработать послать в базу
    // 3 вернуть ответ
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("notUnique",false);
        req.setAttribute("registred",false);
        req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       var parameterMap = req.getParameterMap();
        HttpSession session = req.getSession();
       var isNotValidLogin = regService.checkUniqueLogin(parameterMap.get("login")[0]);
       var isNotValidEmail = regService.checkUniqueEmail(parameterMap.get("email")[0]);
        if(!(isNotValidLogin || isNotValidEmail)) {
            var customer = Customer.builder()
                    .fullName(parameterMap.get("fullname")[0])
                    .login(parameterMap.get("login")[0])
                    .password(parameterMap.get("password")[0])
                    .email(parameterMap.get("email")[0])
                    .build();
        regService.save(customer);
            session.setAttribute("success",true);
            resp.sendRedirect("/login");
        }
        else {
            if(isNotValidLogin){
            req.setAttribute("notUniqueLogin",true);
            }
            else {
                req.setAttribute("notUniqueLogin",false);
            }
            if (isNotValidEmail){
                req.setAttribute("notUniqueEmail",true);
            }
            else {
                req.setAttribute("notUniqueEmail",false);
            }
            req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp")
                    .forward(req,resp);
        }

    }
}
