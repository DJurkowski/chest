package com.app.chess.chest.security.services.room;

import com.app.chess.chest.model.room.Room;
import com.app.chess.chest.security.services.mail.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

import static org.junit.Assert.*;

@RunWith( SpringRunner.class )
@ContextConfiguration
public class RoomServiceImplTest {

    @Autowired
    private RoomService roomService;


    @TestConfiguration
    static class MailServiceTestContextConfiguration {

        @Bean
        public RoomService mailService() {
            return new RoomServiceImpl();
        }
    }

    @Test
    public void initMessage() {
         Room room = new Room();
         String message = "message";

        assertEquals(roomService.initMessage(message,room).getMessage(),"message");
    }
}