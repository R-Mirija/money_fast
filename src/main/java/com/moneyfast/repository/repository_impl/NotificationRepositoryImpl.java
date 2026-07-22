package com.moneyfast.repository.repository_impl;

import java.sql.*;
import com.moneyfast.model.Notification;
import com.moneyfast.repository.DBConnection;
import com.moneyfast.repository.NotificationRepository;

public class NotificationRepositoryImpl implements NotificationRepository {

    @Override
    public void save(Notification notification) {
        String sql = "INSERT INTO notifications (id_transfert, destinataire, type_notification, message, date_envoi, statut_envoi, erreur_message) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setLong(1, notification.getIdTransfert());
            stmt.setLong(2, notification.getDestinataire());
            stmt.setString(3, notification.getTypeNotification());
            stmt.setString(4, notification.getMessage());
            stmt.setTimestamp(5, Timestamp.valueOf(notification.getDateEnvoi()));
            stmt.setString(6, notification.getStatutEnvoi());
            stmt.setString(7, notification.getErreurMessage());
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notification.setIdNotification(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la notification : " + e.getMessage(), e);
        }
    }
}