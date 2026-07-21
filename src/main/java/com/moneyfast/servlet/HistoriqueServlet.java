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
import com.moneyfast.repository.ClientRepository;
import com.moneyfast.repository.CompteRepository;
import com.moneyfast.repository.MySQLClientRepository;
import com.moneyfast.repository.MySQLCompteRepository;
import com.moneyfast.repository.MySQLTransfertRepository;
import com.moneyfast.repository.TransfertRepository;

@WebServlet("/historique")
public class HistoriqueServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ClientRepository clientRepository = new MySQLClientRepository();
    private final CompteRepository compteRepository = new MySQLCompteRepository();
    private final TransfertRepository transfertRepository = new MySQLTransfertRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String telephone = request.getParameter("telephone");

        if (telephone != null && !telephone.trim().isEmpty()) {

            Client client = clientRepository.findByTelephone(telephone);
            if (client != null) {
                request.setAttribute("clientSelected", client);

                Compte compte = compteRepository.findByTelephone(telephone);
                if (compte != null) {
                    request.setAttribute("compteSelected", compte);

                    List<Transfert> operations = transfertRepository.findByCompte(compte.getIdCompte());
                    request.setAttribute("operations", operations);
                }
            } else {
                request.setAttribute("erreur", "Aucun client trouvé avec le numéro : " + telephone);
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/historique.jsp").forward(request, response);
    }
}