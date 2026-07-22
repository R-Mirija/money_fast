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

    if (session != null && session.getAttribute("userClient") != null) {
      resp.sendRedirect(req.getContextPath() + "/client-dashboard");
      return;
    }

    if (session != null && session.getAttribute("userAdmin") != null) {
      resp.sendRedirect(req.getContextPath() + "/admin-dashboard");
      return;
    }

    resp.sendRedirect(req.getContextPath() + "/client-portal");
  }
}