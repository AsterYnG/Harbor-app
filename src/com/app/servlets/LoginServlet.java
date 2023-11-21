package com.app.servlets;

import com.app.entity.Customer;
import com.app.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private int counter = 0;
    private final LoginService loginService = LoginService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("success") != null) {  //это для того чтобы не выскакивало постоянно окно с успешной регистрацией
            if (session.getAttribute("success").equals(true)) {
                if (counter != 0) session.setAttribute("success", false);
                else counter++;
            }
        }

        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();


        Customer customer = Customer.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .build();
        if (loginService.checkUserData(customer)) {

            session.setAttribute("loggedIn", customer);
            // Перенаправление на главную стр в зависимости от роли
        } else {
            req.setAttribute("invalidData", true);
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                    .forward(req, resp);
        }
    }

}
