package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.Notification;

public interface NotificationDAO {
	void create(Notification notification);

	Notification findById(Integer idNotification);

	List<Notification> findByDestinataire(Integer idClient);

	List<Notification> findByTransfert(Integer idTransfert);

	List<Notification> findByStatut(String statutEnvoi);

	void updateStatut(Integer idNotification, String statutEnvoi, String erreurMessage);
}