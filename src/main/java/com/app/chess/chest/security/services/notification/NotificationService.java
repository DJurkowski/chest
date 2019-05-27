package com.app.chess.chest.security.services.notification;

import com.app.chess.chest.model.notification.Notification;

import java.util.List;
import java.util.Set;

public interface NotificationService {
    Notification getNotification(Long id);
    List<Notification> getNotifications(String id);
    void createNotification(String message, Long fromUser, Long toUser);
    boolean updateNotification(Long id, Notification notification);
    boolean deleteNotification(Long id);
    boolean existsById(Long id);
}
