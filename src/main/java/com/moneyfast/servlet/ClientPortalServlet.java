package com.moneyfast.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.moneyfast.model.Client;
import com.moneyfast.model.Compte;
import com.moneyfast.model.Devise;
import com.moneyfast.model.Pays;
import com.moneyfast.repository.*;
import com.moneyfast.repository.repository_impl.ClientRepositoryImpl;
import com.moneyfast.repository.repository_impl.CompteRepositoryImpl;
import com.moneyfast.repository.repository_impl.MetadataRepositoryImpl;
import com.moneyfast.util.PasswordUtil;

@WebServlet("/client-portal")
public class ClientPortalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ClientRepository clientRepository = new ClientRepositoryImpl();
    private final CompteRepository compteRepository = new CompteRepositoryImpl();
    private final MetadataRepository metadataRepository = new MetadataRepositoryImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect("client-portal");
            return;
        }

        List<Pays> listePays = metadataRepository.findAllPays();
        List<Devise> listeDevises = metadataRepository.findAllDevises();
        request.setAttribute("listePays", listePays);
        request.setAttribute("listeDevises", listeDevises);

        request.getRequestDispatcher("/WEB-INF/views/client-portal.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("login".equals(action)) {
            String telephone = request.getParameter("telephone");
            String password = request.getParameter("password");

            Client client = clientRepository.findByTelephone(telephone);

            if (client == null) {

                request.getSession().setAttribute("erreur",
                        "Ce numéro n'a pas encore de compte. Inscrivez-vous ci-dessous !");
                request.setAttribute("prefilledPhone", telephone);
                doGet(request, response);
            }

            if (client != null) {
                String hashedPassword = PasswordUtil.hashPassword(password);

                if (hashedPassword.equals(client.getPassword())) {
                	Compte compteAsso = compteRepository.findByTelephone(telephone);
                    if (compteAsso != null && !"actif".equalsIgnoreCase(compteAsso.getStatutCompte())) {
                        request.getSession().setAttribute("erreur", "Connexion refusée : Votre compte est temporairement bloqué ou suspendu.");
                        doGet(request, response);
                        return;
                    }

                    request.getSession().setAttribute("userClient", client);
                    response.sendRedirect("client-dashboard");
                } else {
                    request.getSession().setAttribute("erreur", "Identifiants invalides.");
                    doGet(request, response);
                }
            }
        }
        else if ("register".equals(action)) {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String telephone = request.getParameter("telephone");
            String mail = request.getParameter("mail");
            int paysId = Integer.parseInt(request.getParameter("pays"));
            int deviseId = Integer.parseInt(request.getParameter("devise"));
            String password = request.getParameter("password");
            String sexe = request.getParameter("sexe");

            if (password.trim().length() < 6) {
                request.getSession().setAttribute("erreur", "Veuillez entrer un mot de passe supérieur à 6 caractères");
            }

            List<Pays> tousLesPays = metadataRepository.findAllPays();
            for (Pays p : tousLesPays) {
                if (p.getIdPays() == paysId && "interdit".equals(p.getStatut())) {
                    request.getSession().setAttribute("erreur",
                            "Inscription refusée : Votre pays de résidence est temporairement suspendu de notre plateforme.");
                    response.sendRedirect("client-portal");
                    return;
                }
            }

            if (clientRepository.findByEmail(mail) != null) {
                request.getSession().setAttribute("erreur", "Erreur : Un client avec cet e-mail existe déjà !");
                response.sendRedirect("client-portal");
                return;
            }
            if (clientRepository.findByTelephone(telephone) != null) {
                request.getSession().setAttribute("erreur", "Erreur : Un client avec ce numéro existe déjà !");
                response.sendRedirect("client-portal");
                return;
            }

            Client nouveauClient = new Client();
            nouveauClient.setNom(nom);
            nouveauClient.setPrenom(prenom);
            nouveauClient.setNumeroTelephone(telephone);
            nouveauClient.setMail(mail);
            nouveauClient.setPays(paysId);
            nouveauClient.setDevisePreferee(deviseId);
            nouveauClient.setPassword(PasswordUtil.hashPassword(password)); // Hachage
            nouveauClient.setSexe(sexe);
            nouveauClient.setDateInscription(LocalDateTime.now());
            nouveauClient.setStatutClient("actif");

            clientRepository.save(nouveauClient);

            Compte nouveauCompte = new Compte();
            nouveauCompte.setIdClient(nouveauClient.getIdClient());
            nouveauCompte.setNumeroCompte(Compte.genererNumero());
            nouveauCompte.setDevise(deviseId);
            nouveauCompte.setSolde(0.00);
            nouveauCompte.setPlafondJournalier(5000.00);
            nouveauCompte.setPlafondTransaction(1000.00);
            nouveauCompte.setDateOuverture(LocalDateTime.now());
            nouveauCompte.setStatutCompte("actif");

            compteRepository.save(nouveauCompte);

            request.getSession().setAttribute("userClient", nouveauClient);
            request.getSession().setAttribute("succes", "Votre compte a été créé et activé avec succès !");
            response.sendRedirect("client-dashboard");
        }
    }
}
