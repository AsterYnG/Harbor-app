package com.app.servlets;

import com.app.dto.FreighterDto;
import com.app.entity.Freighter;
import com.app.entity.VoyageLog;
import com.app.entity.Worker;
import com.app.service.ButtonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@WebServlet("/button")
public class ButtonServlet extends HttpServlet {

    private final ButtonService buttonService = ButtonService.getInstance();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String clickedButton = "";
        Map<String, Boolean> buttons = new HashMap<>();
        var incomingParamatres = req.getParameterMap();
        for (String s : incomingParamatres.keySet()) { // Занос всех параметров button% в Map
            if (s.startsWith("button")) buttons.put(s, Boolean.valueOf(req.getParameter(s)));
        }


        Optional<Map.Entry<String, Boolean>> found = buttons.entrySet().stream()
                .filter(entry -> entry.getValue().equals(true))
                .findFirst();
        clickedButton = found.isPresent() ? found.get().getKey() : "";

        switch (clickedButton) {
            case "buttonAddEmployee": {
                session.setAttribute("active","addEmployee");
                session.setAttribute("positions",buttonService.getAllPositions());
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonShowAllEmployees": {
                var workers = buttonService.showAllEmployees();
                session.setAttribute("searchResult", workers);
                Set<String> columnNames = new HashSet<>();
                for (Field declaredField : workers.get(0).getClass().getDeclaredFields()) {
                    columnNames.add(declaredField.getName());
                }
                session.setAttribute("columnNames", columnNames);
                session.setAttribute("active","showWorkers");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonAddFreighter": {
                session.setAttribute("active","addFreighter");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonShowAllFreighters": {
                var freighters = buttonService.showAllFreighters();
                session.setAttribute("searchResult", freighters);
                Set<String> columnNames = new HashSet<>();
                for (Field declaredField : freighters.get(0).getClass().getDeclaredFields()) {
                    columnNames.add(declaredField.getName());
                }
                session.setAttribute("columnNames", columnNames);
                session.setAttribute("active","showFreighters");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonShowAllClients": {
                var customers = buttonService.showAllCustomers();
                session.setAttribute("searchResult", customers);
                Set<String> columnNames = new HashSet<>();
                for (Field declaredField : customers.get(0).getClass().getDeclaredFields()) {
                    columnNames.add(declaredField.getName());
                }
                session.setAttribute("columnNames", columnNames);
                session.setAttribute("active","showCustomers");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonShowVoyageLog": {
                List<VoyageLog> voyageLog = buttonService.getVoyageLog();
                session.setAttribute("voyageLog", voyageLog);

                session.setAttribute("active","showVoyageLog");
                resp.sendRedirect("/admin");

                break;
            }
            case "buttonDeleteWorker":{
                session.setAttribute("active","deleteWorker");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonUpdateFreighter":{
                List<Freighter> freighters = buttonService.showAllFreighters();
                session.setAttribute("freighters",freighters);
                session.setAttribute("active","updateFreighter");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonAddAvailableRoute": {
                session.setAttribute("active","addAvailableRoute");
                List<Freighter> freighters = buttonService.showAllFreighters();
                session.setAttribute("freighters",freighters);
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonSearch":{
                session.setAttribute("active","showSearch");
                resp.sendRedirect("/admin");
                break;
            }
            case "buttonSort":{
               String currentStatus;
               if (session.getAttribute("active") != null){
                   currentStatus = (String)session.getAttribute("active");
               }else currentStatus = "";

              switch (currentStatus){
                  case "showSearchResultAvailableRoutes":{
                      session.setAttribute("active","sortAvailableRoutes");
                      resp.sendRedirect("/admin");
                      break;
                  }
                  case "showSearchResultCargos":{
                      session.setAttribute("active","sortCargos");
                      resp.sendRedirect("/admin");
                      break;
                  }
                  case "showSearchResultFreighters", "showFreighters":{
                      session.setAttribute("active","sortFreighters");
                      resp.sendRedirect("/admin");
                      break;
                  }
                  case "showSearchResultShips":{
                      session.setAttribute("active","sortShips");
                      resp.sendRedirect("/admin");
                      break;
                  }
                  case "showSearchResultTeams":{
                      session.setAttribute("active","sortTeams");
                      resp.sendRedirect("/admin");
                      break;
                  }
                  case "showSearchResultClients", "showCustomers":{
                      session.setAttribute("active","sortClients");
                      resp.sendRedirect("/admin");
                      break;
                  }
                  case "showSearchResultOrders":{
                      session.setAttribute("active","sortOrders");
                      resp.sendRedirect("/admin");
                      break;
                  }
                  case "showSearchResultWorkers", "showWorkers":{
                      session.setAttribute("active","sortWorkers");
                      resp.sendRedirect("/admin");
                      break;
                  }
                  case "showVoyageLog":{
                      session.setAttribute("active","sortLog");
                      resp.sendRedirect("/admin");
                      break;
                  }


                  default:{
                      resp.sendRedirect("/admin");
                      break;
                  }
              }

            }
        }
    }
}
