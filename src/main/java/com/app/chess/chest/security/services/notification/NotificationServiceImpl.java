package com.app.chess.chest.security.services.notification;

import com.app.chess.chest.model.User;
import com.app.chess.chest.model.exceptions.NotFoundException;
import com.app.chess.chest.model.notification.Notification;
import com.app.chess.chest.repository.NotificationRepository;
import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService{

    public final UserDetailsServiceImpl userService;
    public final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(UserDetailsServiceImpl userService, NotificationRepository notificationRepository) {
        this.userService = userService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification getNotification(Long id) {
        return notificationRepository.findById(id).orElseThrow(() -> new NotFoundException(Notification.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND ));
    }

    @Override
    public Set<Notification> getNotifications(String id) {
        return userService.getUser(userService.getUserId(id)).getNotifications();
    }

    @Override
    public void createNotification(String message, Long fromUser, Long toUser) {
        User user = userService.getUser(toUser);
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setSeen(false);
        notification.setFromUser(fromUser);
        notification.setToUser(toUser);
        notification.setUser(user);
        notificationRepository.save(notification);
    }

    @Override
    public void updateNotification(Long id, Notification notification) {
        if(existsById(id)){
            Notification notification1Now = getNotification(id);
            if(!notification1Now.getSeen().equals(notification.getSeen())){
                notification1Now.setSeen(notification.getSeen());
                notificationRepository.save(notification1Now);
            }
        }
    }

    public boolean existsById(Long id){ return notificationRepository.existsById(id);}

}