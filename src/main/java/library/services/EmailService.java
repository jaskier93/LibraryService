package library.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(String email, String title, String message) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(email);
            helper.setReplyTo("@przykladowy@mail.com");         //:TODO : ustawić maila
            helper.setFrom("@przykladowy@mail.com");            //:TODO : ustawić maila
            helper.setSubject(title);
            helper.setText(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally { //:TODO ?
        }
        javaMailSender.send(mail);
    }
}
