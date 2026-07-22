package com.moneyfast.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneyfast.model.Client;
import com.moneyfast.model.Compte;
import com.moneyfast.model.Devise;
import com.moneyfast.model.Pays;
import com.moneyfast.repository.ClientRepository;
import com.moneyfast.repository.CompteRepository;
import com.moneyfast.repository.MetadataRepository;
import com.moneyfast.repository.repository_impl.ClientRepositoryImpl;
import com.moneyfast.repository.repository_impl.CompteRepositoryImpl;
import com.moneyfast.repository.repository_impl.MetadataRepositoryImpl;


@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final ClientRepository clientRepository = new ClientRepositoryImpl();
    
    private final CompteRepository compteRepository = new CompteRepositoryImpl();
    
    private final MetadataRepository metadataRepository = new MetadataRepositoryImpl();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> liste = clientRepository.findAll();
        request.setAttribute("liste", liste);
        
        List<Pays> listePays = metadataRepository.findAllPays();
        List<Devise> listeDevises = metadataRepository.findAllDevises();
        
        request.setAttribute("listePays", listePays);
        request.setAttribute("listeDevises", listeDevises);
        
        request.getRequestDispatcher("/WEB-INF/views/clients.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String mail = request.getParameter("mail");
        
        int paysId = Integer.parseInt(request.getParameter("pays"));
        int deviseId = Integer.parseInt(request.getParameter("devise"));
        
        String password = request.getParameter("password");
        String sexe = request.getParameter("sexe");
        
        if (clientRepository.findByEmail(mail) != null) {
        	request.getSession().setAttribute("erreur", "Erreur : Un client avec cet e-mail existe déjà !");
            response.sendRedirect("clients");
            return; 
        }//mail

        if (clientRepository.findByTelephone(telephone) != null) {
        	request.getSession().setAttribute("erreur", "Erreur : Un client avec ce numéro de téléphone existe déjà !");
        	response.sendRedirect("clients");
            return;
        }//phone
        
        // clt


        Client nouveauClient = new Client();
        nouveauClient.setNom(nom);
        nouveauClient.setPrenom(prenom);
        nouveauClient.setNumeroTelephone(telephone);
        nouveauClient.setMail(mail);
        nouveauClient.setPays(paysId);
        nouveauClient.setDevisePreferee(deviseId);
        String hashedPassword = com.moneyfast.util.PasswordUtil.hashPassword(password);
        nouveauClient.setPassword(hashedPassword);
        nouveauClient.setSexe(sexe);
        nouveauClient.setDateInscription(LocalDateTime.now());
        nouveauClient.setStatutClient("actif");
        
        clientRepository.save(nouveauClient);
        
        // auto compte
        
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
        
        request.getSession().setAttribute("succes", "Client inscrit avec succès !");
        
        response.sendRedirect("clients");
    
    }
}