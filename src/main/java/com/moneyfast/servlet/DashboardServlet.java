package com.moneyfast.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.moneyfast.repository.MySQLStatsRepository;
import com.moneyfast.repository.StatsRepository;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final StatsRepository statsRepository = new MySQLStatsRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("totalRecettes", statsRepository.getTotalRecettes());
        request.setAttribute("volumeDevises", statsRepository.getVolumeParDevise());
        request.setAttribute("topClients", statsRepository.getTopClients());
        
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}