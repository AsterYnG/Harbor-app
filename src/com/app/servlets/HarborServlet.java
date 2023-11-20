package com.app.servlets;

import com.app.service.HarborService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/registration")
public class HarborServlet extends HttpServlet {
    private final HarborService harborService = HarborService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (var writer = resp.getWriter()) {
            writer.write("<h1>Список перевозщиков</h1>");
            writer.write("<ul>");
            harborService.findAll().forEach(freighterDto -> {
                writer.write("""
                        <li>
                            %d  |   %d  |   %s
                         </li>
                        """.formatted(freighterDto.getFreighterId(),freighterDto.getWeightCost(),freighterDto.getFreighterName()));
            });
            writer.write("</ul>");
        }
    }
}
