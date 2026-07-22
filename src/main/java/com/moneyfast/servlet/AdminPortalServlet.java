package com.moneyfast.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.moneyfast.model.Admin;
import com.moneyfast.repository.AdminRepository;
import com.moneyfast.repository.repository_impl.AdminRepositoryImpl;
import com.moneyfast.util.PasswordUtil;

@WebServlet("/admin-portal")
public class AdminPortalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AdminRepository adminRepository = new AdminRepositoryImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            request.getSession().removeAttribute("userAdmin");
            request.getSession().invalidate();
            response.sendRedirect("admin-portal");
            return;
        }

        boolean hasAdmin = adminRepository.hasAnyAdmin();
        request.setAttribute("hasAdmin", hasAdmin);

        request.getRequestDispatcher("/WEB-INF/views/admin-portal.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            String username = request.getParameter("username");
            String passwordPlain = request.getParameter("password");

            Admin admin = adminRepository.findByUsername(username);

            if (admin != null) {

                String hashedPassword = PasswordUtil.hashPassword(passwordPlain);
                
                if (admin.getPassword().equals(hashedPassword)) {

                    request.getSession().setAttribute("userAdmin", admin);
                    response.sendRedirect("admin-dashboard");
                } else {
                    request.getSession().setAttribute("erreur", "Mot de passe d'administration incorrect !");
                    response.sendRedirect("admin-portal");
                }
            } else {
                request.getSession().setAttribute("erreur", "Nom d'utilisateur administrateur inconnu !");
                response.sendRedirect("admin-portal");
            }
        }
        
        else if ("register".equals(action)) {

            if (adminRepository.hasAnyAdmin()) {
                request.getSession().setAttribute("erreur", "Action non autorisée : Un administrateur existe déjà !");
                response.sendRedirect("admin-portal");
                return;
            }

            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String passwordPlain = request.getParameter("password");

            if (adminRepository.findByUsername(username) != null) {
                request.getSession().setAttribute("erreur", "Erreur : Ce nom d'utilisateur est déjà pris !");
                response.sendRedirect("admin-portal");
                return;
            }
            if (adminRepository.findByEmail(email) != null) {
                request.getSession().setAttribute("erreur", "Erreur : Cet e-mail est déjà utilisé par un autre administrateur !");
                response.sendRedirect("admin-portal");
                return;
            }

            Admin premierAdmin = new Admin();
            premierAdmin.setUsername(username);
            premierAdmin.setEmail(email);
            premierAdmin.setPassword(PasswordUtil.hashPassword(passwordPlain));

            adminRepository.save(premierAdmin);

            request.getSession().setAttribute("userAdmin", premierAdmin);
            request.getSession().setAttribute("succes", "Premier compte Administrateur créé avec succès ! Bienvenue sur MoneyFast.");
            response.sendRedirect("admin-dashboard");
        }
    }
}