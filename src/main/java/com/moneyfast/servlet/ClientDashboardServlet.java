package com.moneyfast.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.moneyfast.model.Client;
import com.moneyfast.model.Compte;
import com.moneyfast.model.Transfert;
import com.moneyfast.repository.*;
import com.moneyfast.service.TransfertService;
import com.moneyfast.util.PasswordUtil;

@WebServlet("/client-dashboard")
public class ClientDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CompteRepository compteRepository = new MySQLCompteRepository();
    private final TransfertRepository transfertRepository = new MySQLTransfertRepository();
    private final MetadataRepository metadataRepository = new MySQLMetadataRepository();
    private final ClientRepository clientRepository = new MySQLClientRepository();
    private final TransfertService transfertService = new TransfertService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Client client = (Client) request.getSession().getAttribute("userClient");
        if (client == null) {
            response.sendRedirect("client-portal");
            return;
        }

        Compte compte = compteRepository.findByTelephone(client.getNumeroTelephone());
        request.setAttribute("userCompte", compte);

        List<Transfert> operations = transfertRepository.findByCompte(compte.getIdCompte());
        request.setAttribute("operations", operations);

        String deviseLabel = metadataRepository.findLibelleDevise(compte.getDevise());
        request.setAttribute("deviseLabel", deviseLabel);

        request.getRequestDispatcher("/WEB-INF/views/client-dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Client client = (Client) request.getSession().getAttribute("userClient");
        if (client == null) {
            response.sendRedirect("client-portal");
            return;
        }

        String action = request.getParameter("action");

        if ("transfert".equals(action)) {
            try {
                String compteDest = request.getParameter("compteDest");
                double montant = Double.parseDouble(request.getParameter("montant"));
                String raison = request.getParameter("raison");
                String passwordConfirm = request.getParameter("passwordConfirm");

                String hashedInput = PasswordUtil.hashPassword(passwordConfirm);
                Client freshClient = clientRepository.findById(client.getIdClient());
                if (!freshClient.getPassword().equals(hashedInput)) {
                    request.getSession().setAttribute("erreur", "Validation échouée : Mot de passe de sécurité incorrect !");
                    response.sendRedirect("client-dashboard");
                    return;
                }

                Transfert t = transfertService.effectuerTransfert(client.getNumeroTelephone(), compteDest, montant, raison);

                request.getSession().setAttribute("succes", "Transfert validé ! Reçu disponible. Code : " + t.getCodeTransfert());
                request.getSession().setAttribute("dernierCodeTransfert", t.getCodeTransfert());
                response.sendRedirect("client-dashboard");

            } catch (NumberFormatException e) {
                request.getSession().setAttribute("erreur", "Erreur : Le format du montant est invalide !");
                response.sendRedirect("client-dashboard");
            } catch (IllegalArgumentException | IllegalStateException e) {
                request.getSession().setAttribute("erreur", e.getMessage());
                response.sendRedirect("client-dashboard");
            }
        }
        
        else if ("depot".equals(action)) {
            try {
                double montantDepot = Double.parseDouble(request.getParameter("montantDepot"));
                if (montantDepot <= 0) {
                    request.getSession().setAttribute("erreur", "Le montant du dépôt doit être supérieur à zéro !");
                    response.sendRedirect("client-dashboard");
                    return;
                }

                Compte compte = compteRepository.findByTelephone(client.getNumeroTelephone());
                double nouveauSolde = compte.getSolde() + montantDepot;

                compteRepository.updateSolde(compte.getNumeroCompte(), nouveauSolde);

                request.getSession().setAttribute("succes", "Dépôt effectué ! Votre compte a été crédité de " + montantDepot + ".");
                response.sendRedirect("client-dashboard");

            } catch (NumberFormatException e) {
                request.getSession().setAttribute("erreur", "Erreur : Le format du montant de dépôt est invalide !");
                response.sendRedirect("client-dashboard");
            }
        }
    }
}