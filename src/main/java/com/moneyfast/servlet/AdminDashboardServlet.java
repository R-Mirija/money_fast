package com.moneyfast.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.moneyfast.model.Admin;
import com.moneyfast.model.Client;
import com.moneyfast.model.Compte;
import com.moneyfast.model.Pays;
import com.moneyfast.model.Transfert;
import com.moneyfast.model.TauxDeChange;
import com.moneyfast.model.Devise;
import com.moneyfast.repository.*;
import com.moneyfast.util.PasswordUtil;

@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AdminRepository adminRepository = new MySQLAdminRepository();
    private final ClientRepository clientRepository = new MySQLClientRepository();
    private final CompteRepository compteRepository = new MySQLCompteRepository();
    private final TransfertRepository transfertRepository = new MySQLTransfertRepository();
    private final MetadataRepository metadataRepository = new MySQLMetadataRepository();
    private final StatsRepository statsRepository = new MySQLStatsRepository();
    private final TauxRepository tauxRepository = new MySQLTauxRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Admin admin = (Admin) request.getSession().getAttribute("userAdmin");
        if (admin == null) {
            response.sendRedirect("admin-portal");
            return;
        }

        List<Client> listeClients = clientRepository.findAll();
        List<Pays> listePays = metadataRepository.findAllPays();
        List<TauxDeChange> listeTaux = tauxRepository.findAll();
        List<Devise> listeDevises = metadataRepository.findAllDevises();
        double totalRecettes = statsRepository.getTotalRecettes();

        request.setAttribute("listeClients", listeClients);
        request.setAttribute("listePays", listePays);
        request.setAttribute("listeTaux", listeTaux);
        request.setAttribute("listeDevises", listeDevises);
        request.setAttribute("totalRecettes", totalRecettes);

        request.getRequestDispatcher("/WEB-INF/views/admin-dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Admin admin = (Admin) request.getSession().getAttribute("userAdmin");
        if (admin == null) {
            response.sendRedirect("admin-portal");
            return;
        }

        String action = request.getParameter("action");

        if ("togglePays".equals(action)) {
            int idPays = Integer.parseInt(request.getParameter("idPays"));
            String statutActuel = request.getParameter("statut");
            String nouveauStatut = "autorise".equals(statutActuel) ? "interdit" : "autorise";

            metadataRepository.updatePaysStatut(idPays, nouveauStatut);
            request.getSession().setAttribute("succes", "Statut du pays mis à jour !");
            response.sendRedirect("admin-dashboard");
        }
        
        else if ("deleteClient".equals(action)) {
            Long idClient = Long.parseLong(request.getParameter("idClient"));
            Client client = clientRepository.findById(idClient);

            if (client != null) {
                Compte compte = compteRepository.findByTelephone(client.getNumeroTelephone());
                
                if (compte != null) {
                    if (compte.getSolde() > 0) {
                        request.getSession().setAttribute("erreur", "Impossible de supprimer : Le solde du client est supérieur à 0 !");
                        response.sendRedirect("admin-dashboard");
                        return;
                    }

                    List<Transfert> operations = transfertRepository.findByCompte(compte.getIdCompte());
                    for (Transfert t : operations) {
                        if ("en attente".equalsIgnoreCase(t.getStatutTransfert()) || "en cours".equalsIgnoreCase(t.getStatutTransfert())) {
                            request.getSession().setAttribute("erreur", "Impossible de supprimer : Le client a des transactions en cours !");
                            response.sendRedirect("admin-dashboard");
                            return;
                        }
                    }
                }

                clientRepository.delete(idClient);
                request.getSession().setAttribute("succes", "Le client a été supprimé.");
            }
            response.sendRedirect("admin-dashboard");
        }
        
        else if ("addAdmin".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String passwordPlain = request.getParameter("password");

            if (adminRepository.findByUsername(username) != null || adminRepository.findByEmail(email) != null) {
                request.getSession().setAttribute("erreur", "Erreur : Ce nom ou cet e-mail est déjà utilisé !");
                response.sendRedirect("admin-dashboard");
                return;
            }

            Admin nouvelAdmin = new Admin();
            nouvelAdmin.setUsername(username);
            nouvelAdmin.setEmail(email);
            nouvelAdmin.setPassword(PasswordUtil.hashPassword(passwordPlain));

            adminRepository.save(nouvelAdmin);
            request.getSession().setAttribute("succes", "Le nouvel administrateur " + username + " a été enregistré !");
            response.sendRedirect("admin-dashboard");
        }

        else if ("addTaux".equals(action)) {
            try {
                int deviseSource = Integer.parseInt(request.getParameter("deviseSource"));
                int deviseDestination = Integer.parseInt(request.getParameter("deviseDestination"));
                float valeurTaux = Float.parseFloat(request.getParameter("valeurTaux"));

                TauxDeChange t = new TauxDeChange();
                t.setCodeTaux((int) (Math.random() * 90000) + 10000);
                t.setDeviseSource(deviseSource);
                t.setDeviseDestination(deviseDestination);
                t.setMontantMin(0.00);
                t.setMontantMax(9999999.00);
                t.setTauxApplication(valeurTaux);
                t.setActive(true);

                tauxRepository.save(t);
                request.getSession().setAttribute("succes", "Nouveau taux de change configuré avec succès !");
            } catch (Exception e) {
                request.getSession().setAttribute("erreur", "Erreur : Format de saisie du taux invalide !");
            }
            response.sendRedirect("admin-dashboard");
        }

        else if ("updateTaux".equals(action)) {
            try {
                int idTaux = Integer.parseInt(request.getParameter("idTaux"));
                float nouveauTaux = Float.parseFloat(request.getParameter("nouveauTaux"));
                
                tauxRepository.updateTaux(idTaux, nouveauTaux, true); // Reste actif après modification
                request.getSession().setAttribute("succes", "La valeur du taux de change a été mise à jour.");
            } catch (Exception e) {
                request.getSession().setAttribute("erreur", "Erreur lors de la mise à jour du taux !");
            }
            response.sendRedirect("admin-dashboard");
        }

        else if ("deleteTaux".equals(action)) {
            try {
                int idTaux = Integer.parseInt(request.getParameter("idTaux"));
                tauxRepository.delete(idTaux);
                request.getSession().setAttribute("succes", "Le taux de change a été supprimé définitivement de la base.");
            } catch (Exception e) {
                request.getSession().setAttribute("erreur", "Erreur de suppression du taux !");
            }
            response.sendRedirect("admin-dashboard");
        }
    }
}