package com.app.servlets;

import com.app.entity.Customer;
import com.app.entity.Order;
import com.app.service.ClientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Or;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    private final ClientService clientService = ClientService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        var loggedCustomer = (Customer) session.getAttribute("loggedIn");


        List<Optional<Order>> orderHistory = clientService.getOrderHistory(loggedCustomer);
//         получаем данные из сервиса

        request.getRequestDispatcher("/WEB-INF/jsp/clientPage.jsp")
                .forward(request,response);
    }
}