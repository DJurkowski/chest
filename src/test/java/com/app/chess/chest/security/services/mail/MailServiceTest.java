package com.app.chess.chest.security.services.mail;

import com.app.chess.chest.model.User;
import org.junit.Before;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

import static org.junit.Assert.*;

@RunWith( SpringRunner.class )
@ContextConfiguration
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @TestConfiguration
    static class MailServiceTestContextConfiguration {

        @Bean
        public MailService mailService() {
            return new MailService(new JavaMailSender() {
                @Override
                public MimeMessage createMimeMessage() {
                    return null;
                }

                @Override
                public MimeMessage createMimeMessage(InputStream inputStream) throws MailException {
                    return null;
                }

                @Override
                public void send(MimeMessage mimeMessage) throws MailException {

                }

                @Override
                public void send(MimeMessage... mimeMessages) throws MailException {

                }

                @Override
                public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {

                }

                @Override
                public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {

                }

                @Override
                public void send(SimpleMailMessage simpleMailMessage) throws MailException {

                }

                @Override
                public void send(SimpleMailMessage... simpleMailMessages) throws MailException {

                }
            });
        }
    }


    @Test
    public void getSimpleMailMessageTest() {

        User user=new User();
        user.setEmail("emial");

        SimpleMailMessage mail = mailService.getSimpleMailMessage(user,"xd");
        assertEquals(user.getEmail(),mail.getTo()[0]);
        assertEquals(1,mail.getTo().length);

    }

}