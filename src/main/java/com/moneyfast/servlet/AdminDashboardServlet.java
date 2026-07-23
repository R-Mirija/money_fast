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
import com.moneyfast.model.Frais;
import com.moneyfast.repository.*;
import com.moneyfast.repository.repository_impl.AdminRepositoryImpl;
import com.moneyfast.repository.repository_impl.ClientRepositoryImpl;
import com.moneyfast.repository.repository_impl.CompteRepositoryImpl;
import com.moneyfast.repository.repository_impl.MetadataRepositoryImpl;
import com.moneyfast.repository.repository_impl.StatsRepositoryImpl;
import com.moneyfast.repository.repository_impl.TauxRepositoryImpl;
import com.moneyfast.repository.repository_impl.TransfertRepositoryImpl;
import com.moneyfast.repository.repository_impl.FraisRepositoryImpl;
import com.moneyfast.util.PasswordUtil;

@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AdminRepository adminRepository = new AdminRepositoryImpl();
    private final ClientRepository clientRepository = new ClientRepositoryImpl();
    private final CompteRepository compteRepository = new CompteRepositoryImpl();
    private final TransfertRepository transfertRepository = new TransfertRepositoryImpl();
    private final MetadataRepository metadataRepository = new MetadataRepositoryImpl();
    private final StatsRepository statsRepository = new StatsRepositoryImpl();
    private final TauxRepository tauxRepository = new TauxRepositoryImpl();
    private final FraisRepository fraisRepository = new FraisRepositoryImpl();

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
        List<Frais> listeFrais = fraisRepository.findAll();
        
        double totalRecettes = statsRepository.getTotalRecettes();

        request.setAttribute("listeClients", listeClients);
        request.setAttribute("listePays", listePays);
        request.setAttribute("listeTaux", listeTaux);
        request.setAttribute("listeDevises", listeDevises);
        request.setAttribute("listeFrais", listeFrais);
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

        // GESTION DES PAYS
        if ("togglePays".equals(action)) {
            int idPays = Integer.parseInt(request.getParameter("idPays"));
            String statutActuel = request.getParameter("statut");
            String nouveauStatut = "autorise".equals(statutActuel) ? "interdit" : "autorise";

            metadataRepository.updatePaysStatut(idPays, nouveauStatut);
            request.getSession().setAttribute("succes", "Statut du pays mis à jour !");
            response.sendRedirect("admin-dashboard");
        }
        
        // SUPPRESSION CLIENTS
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
        
        // AJOUTER UN ADMIN
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

        // CRÉER UN NOUVEAU TAUX
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

        // MODIFIER UN TAUX
        else if ("updateTaux".equals(action)) {
            try {
                int idTaux = Integer.parseInt(request.getParameter("idTaux"));
                float nouveauTaux = Float.parseFloat(request.getParameter("nouveauTaux"));
                
                tauxRepository.updateTaux(idTaux, nouveauTaux, true);
                request.getSession().setAttribute("succes", "La valeur du taux de change a été mise à jour.");
            } catch (Exception e) {
                request.getSession().setAttribute("erreur", "Erreur lors de la mise à jour du taux !");
            }
            response.sendRedirect("admin-dashboard");
        }

        // SUPPRIMER UN TAUX
        else if ("deleteTaux".equals(action)) {
            try {
                int idTaux = Integer.parseInt(request.getParameter("idTaux"));
                tauxRepository.delete(idTaux);
                request.getSession().setAttribute("succes", "Le taux de change a été supprimé définitivement.");
            } catch (Exception e) {
                request.getSession().setAttribute("erreur", "Erreur de suppression du taux !");
            }
            response.sendRedirect("admin-dashboard");
        }

        // FRAIS 
        else if ("addFrais".equals(action)) {
            try {
                int deviseFrais = Integer.parseInt(request.getParameter("deviseFrais"));
                double montantMin = Double.parseDouble(request.getParameter("montantMin"));
                double montantMax = Double.parseDouble(request.getParameter("montantMax"));
                int typeFrais = Integer.parseInt(request.getParameter("typeFrais"));
                double valeurFrais = Double.parseDouble(request.getParameter("valeurFrais"));

                Frais f = new Frais();
                f.setCodeFrais((int) (Math.random() * 90000) + 10000);
                f.setDeviseFrais(deviseFrais);
                f.setMontantMin(montantMin);
                f.setMontantMax(montantMax);
                f.setTypeFrais(typeFrais);
                f.setValeurFrais(valeurFrais);
                f.setActive(true);

                fraisRepository.save(f);
                request.getSession().setAttribute("succes", "Nouveau frais d'envoi enregistré");
            } catch (Exception e) {
                request.getSession().setAttribute("erreur", "Frais invalide !");
            }
            response.sendRedirect("admin-dashboard");
        }
        else if ("updateFrais".equals(action)) {
            try {
                int idFrais = Integer.parseInt(request.getParameter("idFrais"));
                double nouvelleValeur = Double.parseDouble(request.getParameter("nouvelleValeur").replace(',', '.'));

                Frais f = fraisRepository.findById(idFrais);
                if (f != null) {
                    f.setValeurFrais(nouvelleValeur);
                    fraisRepository.update(f);
                    request.getSession().setAttribute("succes", "Frais mis à jour.");
                }
            } catch (Exception e) {
                request.getSession().setAttribute("erreur", "Erreur lors de la mise à jour des frais !");
            }
            response.sendRedirect("admin-dashboard");
        }
        else if ("deleteFrais".equals(action)) {
            try {
                int idFrais = Integer.parseInt(request.getParameter("idFrais"));
                fraisRepository.delete(idFrais);
                request.getSession().setAttribute("succes", "Frais d'envoi supprimé.");
            } catch (Exception e) {
                request.getSession().setAttribute("erreur", "Erreur lors de la suppression des frais !");
            }
            response.sendRedirect("admin-dashboard");
        }
    }
}
