package com.app.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/client", "/admin", "/button", "/logout"})
public class Filter implements jakarta.servlet.Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        var tempRole = session.getAttribute("role");
        boolean isAdmin = false;
        boolean isClient = false;

        if (tempRole == null) { // Проверка роли пользователя, если такого нет то редирект на логин
            resp.sendRedirect("/login");
        } else {
            var role = (String) tempRole;

            if (role.equals("admin")) {
                filterChain.doFilter(servletRequest, servletResponse);
                isAdmin = true;
            }
            if (role.equals("user")) {
                filterChain.doFilter(servletRequest, servletResponse);
                isClient = true;
            }
            if (!(isAdmin || isClient)) {
                resp.sendRedirect("/login");
            }
        }

//        catch (Exception e){
//            resp.sendRedirect("/login");
//        }

    }
}
