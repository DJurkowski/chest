package com.app.chess.chest.security.services.mail;

import com.app.chess.chest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(User user, String password) throws MailException {

        SimpleMailMessage mail = getSimpleMailMessage(user, password);

        javaMailSender.send(mail);
    }

    public SimpleMailMessage getSimpleMailMessage(User user, String password) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Chess Application Password Reset");
        mail.setText("Your password has been changed.\nYour new password is : " + password +" \n Have a nice day!" +
                "\nTeam ChessApp.");
        return mail;
    }
}
