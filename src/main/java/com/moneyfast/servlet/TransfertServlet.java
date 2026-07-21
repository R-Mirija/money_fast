package com.moneyfast.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.moneyfast.model.Transfert;
import com.moneyfast.service.TransfertService;

@WebServlet("/transfert")
public class TransfertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final TransfertService transfertService = new TransfertService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/transfert.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupération données du formulaire
            String compteSourceStr = request.getParameter("compteSource");
            String compteDestStr = request.getParameter("compteDest");
            double montant = Double.parseDouble(request.getParameter("montant"));
            String raison = request.getParameter("raison");

            // Exécution du transfert via le service métier
            Transfert t = transfertService.effectuerTransfert(compteSourceStr, compteDestStr, montant, raison);

            // Notification succès 
            request.getSession().setAttribute("succes", "Transfert de " + montant + " EUR effectué avec succès ! Code de suivi : " + t.getCodeTransfert());
            response.sendRedirect("transfert");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("erreur", "Erreur : Le format du montant est invalide !");
            response.sendRedirect("transfert");
        } catch (IllegalArgumentException | IllegalStateException e) {

            request.getSession().setAttribute("erreur", e.getMessage());
            response.sendRedirect("transfert");
        }
    }
}