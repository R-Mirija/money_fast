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
import com.moneyfast.model.Devise;
import com.moneyfast.model.Pays;
import com.moneyfast.model.Transfert;
import com.moneyfast.repository.*;
import com.moneyfast.repository.repository_impl.ClientRepositoryImpl;
import com.moneyfast.repository.repository_impl.CompteRepositoryImpl;
import com.moneyfast.repository.repository_impl.MetadataRepositoryImpl;
import com.moneyfast.repository.repository_impl.TransfertRepositoryImpl;
import com.moneyfast.service.TransfertService;
import com.moneyfast.util.PasswordUtil;

@WebServlet("/client-dashboard")
public class ClientDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CompteRepository compteRepository = new CompteRepositoryImpl();
    private final TransfertRepository transfertRepository = new TransfertRepositoryImpl();
    private final MetadataRepository metadataRepository = new MetadataRepositoryImpl();
    private final ClientRepository clientRepository = new ClientRepositoryImpl();
    private final TransfertService transfertService = new TransfertService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Client client = (Client) request.getSession().getAttribute("userClient");
        if (client == null) {
            response.sendRedirect("client-portal");
            return;
        }
        Client freshClient = clientRepository.findById(client.getIdClient());
        request.getSession().setAttribute("userClient", freshClient);

        Compte compte = compteRepository.findByTelephone(freshClient.getNumeroTelephone());
        request.setAttribute("userCompte", compte);

        List<Transfert> operations = transfertRepository.findByCompte(compte.getIdCompte());
        request.setAttribute("operations", operations);

        String deviseLabel = metadataRepository.findLibelleDevise(compte.getDevise());
        request.setAttribute("deviseLabel", deviseLabel);
        List<Pays> listePays = metadataRepository.findAllPays();
        List<Devise> listeDevises = metadataRepository.findAllDevises();
        request.setAttribute("listePays", listePays);
        request.setAttribute("listeDevises", listeDevises);

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
        }else if ("depot".equals(action)) {
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
        }else if ("updateProfile".equals(action)) {
            try {
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String telephone = request.getParameter("telephone");
                String mail = request.getParameter("mail");
                int paysId = Integer.parseInt(request.getParameter("pays"));
                int deviseId = Integer.parseInt(request.getParameter("devise"));
                String passwordConfirm = request.getParameter("passwordConfirm");

                String hashedInput = PasswordUtil.hashPassword(passwordConfirm);
                Client freshClient = clientRepository.findById(client.getIdClient());
                if (!freshClient.getPassword().equals(hashedInput)) {
                    request.getSession().setAttribute("erreur", "Modification refusée : Mot de passe de confirmation incorrect !");
                    response.sendRedirect("client-dashboard");
                    return;
                }
                Client existingEmail = clientRepository.findByEmail(mail);
                if (existingEmail != null && !existingEmail.getIdClient().equals(client.getIdClient())) {
                    request.getSession().setAttribute("erreur", "Modification refusée : Cet e-mail est déjà utilisé !");
                    response.sendRedirect("client-dashboard");
                    return;
                }

                Client existingPhone = clientRepository.findByTelephone(telephone);
                if (existingPhone != null && !existingPhone.getIdClient().equals(client.getIdClient())) {
                    request.getSession().setAttribute("erreur", "Modification refusée : Ce numéro de téléphone est déjà pris !");
                    response.sendRedirect("client-dashboard");
                    return;
                }

                List<Pays> tousLesPays = metadataRepository.findAllPays();
                for (Pays p : tousLesPays) {
                    if (p.getIdPays() == paysId && "interdit".equals(p.getStatut())) {
                        request.getSession().setAttribute("erreur", "Modification refusée : Votre nouveau pays de résidence est suspendu de la plateforme.");
                        response.sendRedirect("client-dashboard");
                        return;
                    }
                }
                freshClient.setNom(nom);
                freshClient.setPrenom(prenom);
                freshClient.setNumeroTelephone(telephone);
                freshClient.setMail(mail);
                freshClient.setPays(paysId);
                freshClient.setDevisePreferee(deviseId);

                clientRepository.update(freshClient);

                request.getSession().setAttribute("userClient", freshClient); // Mettre à jour la session
                request.getSession().setAttribute("succes", "Votre profil a été mis à jour avec succès !");
                response.sendRedirect("client-dashboard");

            } catch (Exception e) {
                request.getSession().setAttribute("erreur", "Erreur lors de la mise à jour : " + e.getMessage());
                response.sendRedirect("client-dashboard");
            }
        }else if ("changePassword".equals(action)) {
            try {
                String currentPassword = request.getParameter("currentPassword");
                String newPassword = request.getParameter("newPassword");

                // Vérif mot de passe actuel
                String hashedCurrent = PasswordUtil.hashPassword(currentPassword);
                Client freshClient = clientRepository.findById(client.getIdClient());
                if (!freshClient.getPassword().equals(hashedCurrent)) {
                    request.getSession().setAttribute("erreur", "Échec : Le mot de passe actuel est incorrect !");
                    response.sendRedirect("client-dashboard");
                    return;
                }

                // Vérif longueur nouveau mot de passe 
                if (newPassword == null || newPassword.trim().length() < 6) {
                    request.getSession().setAttribute("erreur", "Échec : Le nouveau mot de passe doit faire au moins 6 caractères !");
                    response.sendRedirect("client-dashboard");
                    return;
                }
                String hashedNewPassword = PasswordUtil.hashPassword(newPassword);
                clientRepository.updatePassword(freshClient.getIdClient(), hashedNewPassword);
                freshClient.setPassword(hashedNewPassword);
                request.getSession().setAttribute("userClient", freshClient); 
                
                request.getSession().setAttribute("succes", "Votre mot de passe a été modifié avec succès !");
                response.sendRedirect("client-dashboard");

            } catch (Exception e) {
                request.getSession().setAttribute("erreur", "Erreur de mise à jour du mot de passe : " + e.getMessage());
                response.sendRedirect("client-dashboard");
            }
        }
    }
}
