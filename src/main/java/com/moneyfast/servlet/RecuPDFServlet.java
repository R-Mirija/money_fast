package com.moneyfast.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.moneyfast.model.Client;
import com.moneyfast.model.Compte;
import com.moneyfast.model.Transfert;
import com.moneyfast.repository.*;

@WebServlet("/recu-pdf")
public class RecuPDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final TransfertRepository transfertRepository = new MySQLTransfertRepository();
    private final CompteRepository compteRepository = new MySQLCompteRepository();
    private final ClientRepository clientRepository = new MySQLClientRepository();
    private final MetadataRepository metadataRepository = new MySQLMetadataRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codeStr = request.getParameter("code");

        if (codeStr == null || codeStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Code de transfert manquant !");
            return;
        }

        try {
            int codeTransfert = Integer.parseInt(codeStr);
            
            Transfert t = transfertRepository.findByCode(codeTransfert);
            if (t == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transfert introuvable !");
                return;
            }

            Compte compteSource = compteRepository.findById(t.getIdCompteSource());
            Compte compteDest = compteRepository.findById(t.getIdCompteDestination());
            
            Client expediteur = clientRepository.findById(compteSource.getIdClient());
            Client destinataire = clientRepository.findById(compteDest.getIdClient());
            
            String deviseSource = metadataRepository.findLibelleDevise(t.getDeviseSource());
            String deviseDest = metadataRepository.findLibelleDevise(t.getDeviseDestination());

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=recu_moneyfast_" + codeTransfert + ".pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.RED);
            Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.GRAY);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.BLACK);

            Paragraph title = new Paragraph("MONEYFAST", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subtitle = new Paragraph("REÇU DE TRANSFERT D'ARGENT EN LIGNE", subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(30);
            document.add(subtitle);

            document.add(new Paragraph("Référence externe : " + t.getReferenceExterne(), normalFont));
            document.add(new Paragraph("Code de Transaction : " + t.getCodeTransfert(), boldFont));
            document.add(new Paragraph("Date du transfert : " + t.getDateTransfert(), normalFont));
            document.add(new Paragraph("Motif : " + t.getRaison(), normalFont));
            document.add(new Paragraph("Statut : " + t.getStatutTransfert().toUpperCase(), boldFont));
            document.add(new Paragraph("                          "));

            // Section Expéditeur
            document.add(new Paragraph("EXPÉDITEUR :", boldFont));
            document.add(new Paragraph("Nom complet : " + expediteur.getNom().toUpperCase() + " " + expediteur.getPrenom(), normalFont));
            document.add(new Paragraph("Téléphone : " + expediteur.getNumeroTelephone(), normalFont));
            document.add(new Paragraph("Montant envoyé : " + t.getMontantEnvoye() + " " + deviseSource, boldFont));
            document.add(new Paragraph("Frais d'envoi appliqués : " + t.getFrais() + " " + deviseSource, normalFont));
            document.add(new Paragraph("                          "));

            // Section Bénéficiaire
            document.add(new Paragraph("BÉNÉFICIAIRE :", boldFont));
            document.add(new Paragraph("Nom complet : " + destinataire.getNom().toUpperCase() + " " + destinataire.getPrenom(), normalFont));
            document.add(new Paragraph("Téléphone : " + destinataire.getNumeroTelephone(), normalFont));
            document.add(new Paragraph("Montant reçu : " + t.getMontantRecu() + " " + deviseDest, boldFont));
            document.add(new Paragraph("                          "));

            // Pied de page
            Paragraph footer = new Paragraph("Merci d'avoir choisi MoneyFast pour vos transferts rapides.", subtitleFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(40);
            document.add(footer);

            document.close();

        } catch (Exception e) {
            throw new ServletException("Impossible de générer le reçu PDF", e);
        }
    }
}