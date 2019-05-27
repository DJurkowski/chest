package com.app.chess.chest.security.services.notification;

import com.app.chess.chest.model.exceptions.NotFoundException;
import com.app.chess.chest.model.notification.Notification;
import com.app.chess.chest.repository.NotificationRepository;
import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import com.app.chess.chest.security.services.room.RoomService;
import com.app.chess.chest.security.services.room.RoomServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith( SpringRunner.class )
@SpringBootTest
public class NotificationServiceImplTest {

    @Autowired
    NotificationService notificationService;

    @MockBean
    NotificationRepository notificationRepository;


    @TestConfiguration
    class NotificationServiceTestContextConfiguration {

        @Bean
        public NotificationService notificaitonService() {
            return new NotificationServiceImpl();
        }


    }

    @Test
    public void updateNotification() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setSeen(false);

        Notification notificationSeen = new Notification();
        notificationSeen.setSeen(true);

        Mockito.when(notificationService.existsById(1L)).thenReturn(true);
        Mockito.when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        Mockito.when(notificationRepository.save(notification)).thenReturn(notification);

        notificationService.updateNotification(1L,notificationSeen);
        assertTrue(notification.getSeen());
    }

    @Test(expected = NotFoundException.class)
    public void updateNotification_withEx() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setSeen(false);

        Notification notificationSeen = new Notification();
        notificationSeen.setSeen(true);

        Mockito.when(notificationService.existsById(1L)).thenReturn(true);
        Mockito.when(notificationRepository.findById(1L)).thenReturn(Optional.empty());
        notificationService.updateNotification(1L,notificationSeen);

    }

    @Test()
    public void updateNotification_withFalse() {
        Notification notification = new Notification();

        Mockito.when(notificationService.existsById(1L)).thenReturn(false);
        assertFalse(notificationService.updateNotification(1L,notification));

    }

    @Test()
    public void deleteNotification() {
        Notification notification = new Notification();
        notification.setId(1L);

        Mockito.when(notificationService.existsById(1L)).thenReturn(true);
//        Mockito.verify(notificationRepository).deleteById(1L);
        assertTrue(notificationService.deleteNotification(1L));

    }



    @Test(expected = NotFoundException.class)
    public void deleteNotification_wiEx() {
        Mockito.when(notificationService.existsById(1L)).thenReturn(false);
        notificationService.deleteNotification(1L);
    }

//    @Test()
//    public void createNotification() {
//
//    }
}