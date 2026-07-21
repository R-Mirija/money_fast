package com.moneyfast.model;

import java.time.LocalDateTime;

public class Notification {
    private Long idNotification;
    private Long idTransfert;
    private Long destinataire;
    private String typeNotification;
    private String message;
    private LocalDateTime dateEnvoi;
    private String statutEnvoi;
    private String erreurMessage;

    public Notification() {}

    public Notification(Long idNotification, Long idTransfert, Long destinataire, String typeNotification, 
                        String message, LocalDateTime dateEnvoi, String statutEnvoi, String erreurMessage) {
        this.idNotification = idNotification;
        this.idTransfert = idTransfert;
        this.destinataire = destinataire;
        this.typeNotification = typeNotification;
        this.message = message;
        this.dateEnvoi = dateEnvoi;
        this.statutEnvoi = statutEnvoi;
        this.erreurMessage = erreurMessage;
    }

    public Long getIdNotification() { return idNotification; }
    public void setIdNotification(Long idNotification) { this.idNotification = idNotification; }
    public Long getIdTransfert() { return idTransfert; }
    public void setIdTransfert(Long idTransfert) { this.idTransfert = idTransfert; }
    public Long getDestinataire() { return destinataire; }
    public void setDestinataire(Long destinataire) { this.destinataire = destinataire; }
    public String getTypeNotification() { return typeNotification; }
    public void setTypeNotification(String typeNotification) { this.typeNotification = typeNotification; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getDateEnvoi() { return dateEnvoi; }
    public void setDateEnvoi(LocalDateTime dateEnvoi) { this.dateEnvoi = dateEnvoi; }
    public String getStatutEnvoi() { return statutEnvoi; }
    public void setStatutEnvoi(String statutEnvoi) { this.statutEnvoi = statutEnvoi; }
    public String getErreurMessage() { return erreurMessage; }
    public void setErreurMessage(String erreurMessage) { this.erreurMessage = erreurMessage; }
}