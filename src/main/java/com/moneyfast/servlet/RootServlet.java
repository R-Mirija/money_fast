package com.moneyfast.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("")
public class RootServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    String viewPath = "/WEB-INF/views";

    if (session != null && session.getAttribute("userClient") != null) {
      req.getRequestDispatcher(viewPath + "/client-dashboard.jsp").forward(req, resp);
      return;
    }

    if (session != null && session.getAttribute("userAdmin") != null) {
      req.getRequestDispatcher(viewPath + "/admin-dashboard.jsp").forward(req, resp);
      return;
    }

    req.getRequestDispatcher(viewPath + "/client-portal.jsp").forward(req, resp);
  }
}