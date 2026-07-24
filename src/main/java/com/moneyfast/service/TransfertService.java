package com.moneyfast.service;

import java.time.LocalDateTime;

import com.moneyfast.enums.StatutTransfertEnum;
import com.moneyfast.model.Client;
import com.moneyfast.model.Compte;
import com.moneyfast.model.Frais;
import com.moneyfast.model.Notification;
import com.moneyfast.model.Transfert;
import com.moneyfast.model.TauxDeChange;
import com.moneyfast.repository.ClientRepository;
import com.moneyfast.repository.CompteRepository;
import com.moneyfast.repository.FraisRepository;
import com.moneyfast.repository.MetadataRepository;
import com.moneyfast.repository.NotificationRepository;
import com.moneyfast.repository.TauxRepository;
import com.moneyfast.repository.TransfertRepository;
import com.moneyfast.repository.repository_impl.ClientRepositoryImpl;
import com.moneyfast.repository.repository_impl.CompteRepositoryImpl;
import com.moneyfast.repository.repository_impl.FraisRepositoryImpl;
import com.moneyfast.repository.repository_impl.MetadataRepositoryImpl;
import com.moneyfast.repository.repository_impl.NotificationRepositoryImpl;
import com.moneyfast.repository.repository_impl.TauxRepositoryImpl;
import com.moneyfast.repository.repository_impl.TransfertRepositoryImpl;

public class TransfertService {

    private final CompteRepository compteRepository = new CompteRepositoryImpl();
    private final TransfertRepository transfertRepository = new TransfertRepositoryImpl();
    private final NotificationRepository notificationRepository = new NotificationRepositoryImpl();
    private final ClientRepository clientRepository = new ClientRepositoryImpl();
    private final MetadataRepository metadataRepository = new MetadataRepositoryImpl();
    private final TauxRepository tauxRepository = new TauxRepositoryImpl();
    private final FraisRepository fraisRepository = new FraisRepositoryImpl();

    public Transfert effectuerTransfert(String telCompteSource, String telCompteDest, double montantEnvoye,
            String raison) {

        Compte source = compteRepository.findByTelephone(telCompteSource);
        Compte dest = compteRepository.findByTelephone(telCompteDest);

        if (source == null) {
            throw new IllegalArgumentException("Le numéro de téléphone de l'expéditeur est introuvable !");
        }
        if (dest == null) {
            throw new IllegalArgumentException("Le numéro de téléphone du destinataire est introuvable !");
        }

        if (!source.getStatutCompte().equalsIgnoreCase("actif")) {
            throw new IllegalStateException("Le compte expéditeur est inactif ou bloqué !");
        }
        if (!dest.getStatutCompte().equalsIgnoreCase("actif")) {
            throw new IllegalStateException("Le compte destinataire est inactif ou bloqué !");
        }
        if (montantEnvoye <= 0) {
            throw new IllegalArgumentException("Le montant du transfert doit être supérieur à zéro !");
        }
        if (source.getIdClient().equals(dest.getIdClient())) {
            throw new IllegalArgumentException(
                    "Vous ne pouvez pas effectuer un transfert vers l'un de vos propres comptes !");
        }
        if (montantEnvoye > source.getPlafondTransaction()) {
            throw new IllegalArgumentException("Le montant dépasse le plafond autorisé par transaction ("
                    + source.getPlafondTransaction() + " EUR) !");
        }

        // application du frais
        Frais frais = fraisRepository.findApplicable(source.getDevise(), montantEnvoye);
        double montantFrais = 0.0;

        if (frais != null) {
            montantFrais = (frais.getTypeFrais() == 1) ? montantEnvoye * (frais.getValeurFrais() / 100.0)
                    : frais.getValeurFrais();
        } else {
            double TAUX_PAR_DEFAUT = 0.10; // 10% by default
            montantFrais = montantEnvoye * TAUX_PAR_DEFAUT;
        }

        // application du taux
        TauxDeChange tauxObj = tauxRepository.findActifByDevises(source.getDevise(), dest.getDevise(), montantEnvoye);
        if (tauxObj == null) {
            throw new IllegalStateException(
                    "Aucun taux de change actif trouvé en base de données pour cette conversion de devise et cette tranche de montant !");
        }

        double taux = tauxObj.getTauxApplication();
        int idTauxChange = tauxObj.getIdTaux();

        double coutTotalExpediteur = montantEnvoye + montantFrais;

        if (source.getSolde() < coutTotalExpediteur) {
            throw new IllegalStateException("Solde insuffisant ! Solde requis : " + coutTotalExpediteur + " "
                    + metadataRepository.findLibelleDevise(source.getDevise()) + " (frais inclus).");
        }

        double montantRecu = montantEnvoye * taux;

        double nouveauSoldeSource = source.getSolde() - coutTotalExpediteur;
        double nouveauSoldeDest = dest.getSolde() + montantRecu;

        compteRepository.updateSolde(source.getNumeroCompte(), nouveauSoldeSource);
        compteRepository.updateSolde(dest.getNumeroCompte(), nouveauSoldeDest);

        Transfert t = new Transfert();
        t.setCodeTransfert(Transfert.genererCodeTransfert());
        t.setIdCompteSource(source.getIdCompte());
        t.setIdCompteDestination(dest.getIdCompte());
        t.setMontantEnvoye(montantEnvoye);
        t.setMontantRecu(montantRecu);
        t.setFrais(montantFrais);
        t.setTauxApplique(idTauxChange);
        t.setDeviseSource(source.getDevise());
        t.setDeviseDestination(dest.getDevise());
        t.setDateTransfert(LocalDateTime.now());
        t.setRaison(raison);
        t.setStatutTransfert(StatutTransfertEnum.COMPLETE);
        t.setDateConfirmation(LocalDateTime.now());
        t.setReferenceExterne("EXT-" + t.getCodeTransfert());

        transfertRepository.save(t);

        Client expediteur = clientRepository.findById(source.getIdClient());
        Client destinataire = clientRepository.findById(dest.getIdClient());

        String deviseSourceStr = metadataRepository.findLibelleDevise(source.getDevise());
        String deviseDestStr = metadataRepository.findLibelleDevise(dest.getDevise());

        String msgExpediteur = "Votre transfert de " + t.getMontantEnvoye() + " " + deviseSourceStr
                + " au " + destinataire.getNumeroTelephone()
                + " a été effectué avec succès. Motif : " + t.getRaison();

        Notification notifExpediteur = new Notification();
        notifExpediteur.setIdTransfert(t.getIdTransfert());
        notifExpediteur.setDestinataire(source.getIdClient());
        notifExpediteur.setTypeNotification("Mail");
        notifExpediteur.setMessage(msgExpediteur);
        notifExpediteur.setDateEnvoi(LocalDateTime.now());
        notifExpediteur.setStatutEnvoi("envoyé");
        notifExpediteur.setErreurMessage("");

        notificationRepository.save(notifExpediteur);

        String msgDestinataire = "Vous avez reçu " + t.getMontantRecu() + " " + deviseDestStr
                + " de la part de " + expediteur.getNumeroTelephone()
                + ". Motif : " + t.getRaison();

        Notification notifDestinataire = new Notification();
        notifDestinataire.setIdTransfert(t.getIdTransfert());
        notifDestinataire.setDestinataire(dest.getIdClient());
        notifDestinataire.setTypeNotification("SMS");
        notifDestinataire.setMessage(msgDestinataire);
        notifDestinataire.setDateEnvoi(LocalDateTime.now());
        notifDestinataire.setStatutEnvoi("envoyé");
        notifDestinataire.setErreurMessage("");

        notificationRepository.save(notifDestinataire);

        System.out.println("\nNOTIFICATIONS");
        System.out.println("[ENVOI MAIL] À : " + expediteur.getMail() + " -> " + msgExpediteur);
        System.out.println("[ENVOI SMS] À : " + destinataire.getNumeroTelephone() + " -> " + msgDestinataire);

        return t;
    }
}