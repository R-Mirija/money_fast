package com.money_fast.daos;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.money_fast.beans.enums.StatutEnvoi;
import com.money_fast.beans.enums.TypeNotification;

public class NotificationDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idNotification;
	private Integer idTransfert;
	private Integer destinataire;
	private TypeNotification typeNotification;
	private String message;
	private LocalDateTime dateEnvoi;
	private StatutEnvoi statutEnvoi;
	private String erreurMessage;

	public NotificationDAO() {
	}

	// Getters & Setters ...
	public Integer getIdNotification() {
		return idNotification;
	}

	public void setIdNotification(Integer idNotification) {
		this.idNotification = idNotification;
	}

	public Integer getIdTransfert() {
		return idTransfert;
	}

	public void setIdTransfert(Integer idTransfert) {
		this.idTransfert = idTransfert;
	}

	public Integer getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(Integer destinataire) {
		this.destinataire = destinataire;
	}

	public TypeNotification getTypeNotification() {
		return typeNotification;
	}

	public void setTypeNotification(TypeNotification typeNotification) {
		this.typeNotification = typeNotification;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDateEnvoi() {
		return dateEnvoi;
	}

	public void setDateEnvoi(LocalDateTime dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	public StatutEnvoi getStatutEnvoi() {
		return statutEnvoi;
	}

	public void setStatutEnvoi(StatutEnvoi statutEnvoi) {
		this.statutEnvoi = statutEnvoi;
	}

	public String getErreurMessage() {
		return erreurMessage;
	}

	public void setErreurMessage(String erreurMessage) {
		this.erreurMessage = erreurMessage;
	}
}