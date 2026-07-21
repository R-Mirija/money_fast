package com.moneyfast.repository;

import com.moneyfast.model.Notification;

public interface NotificationRepository {
    void save(Notification notification);
}