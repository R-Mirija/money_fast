package com.moneyfast.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.moneyfast.model.Client;
import com.moneyfast.model.Compte;
import com.moneyfast.model.Transfert;
import com.moneyfast.repository.*;

@WebServlet("/releve-pdf")
public class RelevePDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ClientRepository clientRepository = new MySQLClientRepository();
    private final CompteRepository compteRepository = new MySQLCompteRepository();
    private final TransfertRepository transfertRepository = new MySQLTransfertRepository();
    private final MetadataRepository metadataRepository = new MySQLMetadataRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String telephone = request.getParameter("telephone");
        String moisStr = request.getParameter("mois"); 

        if (telephone == null || moisStr == null || !moisStr.contains("-")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres d'appel invalides ! (telephone et mois au format AAAA-MM requis)");
            return;
        }

        try {
            // Extraction de l'année et du mois
            String[] parts = moisStr.split("-");
            int annee = Integer.parseInt(parts[0]);
            int mois = Integer.parseInt(parts[1]);

            // Charger le client et son compte
            Client client = clientRepository.findByTelephone(telephone);
            if (client == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Client introuvable !");
                return;
            }

            Compte compte = compteRepository.findByTelephone(telephone);
            if (compte == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Compte introuvable !");
                return;
            }

            String deviseLabel = metadataRepository.findLibelleDevise(compte.getDevise());

            // Récupérer les opérations de ce mois
            List<Transfert> operations = transfertRepository.findByCompteAndMonth(compte.getIdCompte(), annee, mois);

            // Configurer la réponse HTTP pour le PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=releve_" + moisStr + "_" + telephone + ".pdf");

            // Générer le document PDF
            Document document = new Document(PageSize.A4, 36, 36, 54, 36);
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            // Polices
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.DARK_GRAY);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK);

            Paragraph mainTitle = new Paragraph("MONEYFAST - RELEVÉ DE COMPTE MENSUEL", titleFont);
            mainTitle.setAlignment(Element.ALIGN_CENTER);
            mainTitle.setSpacingAfter(20);
            document.add(mainTitle);

            // Informations client et période
            document.add(new Paragraph("Titulaire du Compte : " + client.getNom().toUpperCase() + " " + client.getPrenom(), boldFont));
            document.add(new Paragraph("Téléphone : " + client.getNumeroTelephone(), normalFont));
            document.add(new Paragraph("Numéro de Compte : " + compte.getNumeroCompte(), normalFont));
            document.add(new Paragraph("Période du relevé : " + moisStr, boldFont));
            document.add(new Paragraph("Solde de référence actuel : " + compte.getSolde() + " " + deviseLabel, normalFont));
            document.add(new Paragraph("                          "));

            // Tableau des transactions
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{15f, 20f, 35f, 15f, 15f});

            table.addCell(new PdfPCell(new Phrase("Date", boldFont)));
            table.addCell(new PdfPCell(new Phrase("Type", boldFont)));
            table.addCell(new PdfPCell(new Phrase("Motif", boldFont)));
            table.addCell(new PdfPCell(new Phrase("Débit (-)", boldFont)));
            table.addCell(new PdfPCell(new Phrase("Crédit (+)", boldFont)));

            double totalDebits = 0;
            double totalCredits = 0;

            for (Transfert t : operations) {
                boolean isDebit = t.getIdCompteSource().equals(compte.getIdCompte());

                // Date
                table.addCell(new PdfPCell(new Phrase(t.getDateTransfert().toLocalDate().toString(), normalFont)));

                // Type
                table.addCell(new PdfPCell(new Phrase(isDebit ? "DÉBIT (Envoi)" : "CRÉDIT (Réception)", normalFont)));

                // Motif
                table.addCell(new PdfPCell(new Phrase(t.getRaison(), normalFont)));

                // Débit et Crédit
                if (isDebit) {
                    table.addCell(new PdfPCell(new Phrase("-" + t.getMontantEnvoye(), normalFont)));
                    table.addCell(new PdfPCell(new Phrase("", normalFont))); // Case crédit
                    totalDebits += t.getMontantEnvoye();
                } else {
                    table.addCell(new PdfPCell(new Phrase("", normalFont))); // Case débit
                    table.addCell(new PdfPCell(new Phrase("+" + t.getMontantRecu(), normalFont)));
                    totalCredits += t.getMontantRecu();
                }
            }

            document.add(table);
            document.add(new Paragraph("\n"));

            // Synthèse financière du mois
            document.add(new Paragraph("RÉCAPITULATIF DU MOIS :", boldFont));
            document.add(new Paragraph("Total de vos Envois (Débits) : " + totalDebits + " " + deviseLabel, normalFont));
            document.add(new Paragraph("Total de vos Réceptions (Crédits) : " + totalCredits + " " + deviseLabel, normalFont));
            document.add(new Paragraph("                          "));

            Paragraph footer = new Paragraph("Généré automatiquement par MoneyFast. Document conforme aux règles de gestion d'entreprise.", sectionFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            document.add(footer);

            document.close();

        } catch (Exception e) {
            throw new ServletException("Erreur de génération du relevé mensuel PDF", e);
        }
    }
}