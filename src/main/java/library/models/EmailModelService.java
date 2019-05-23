package library.models;

import library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailModelService {
    private JavaMailSender javaMailSender;
    private UserRepository userRepository;

    @Autowired
    public EmailModelService(JavaMailSender javaMailSender, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    public void sendMail(String title, String mailMessage, Integer userId) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        String email = userRepository.getOne(userId).getEmail();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(email);
            helper.setReplyTo("@przykladowy@mail.com");         //:TODO : ustawić maila
            helper.setFrom("@przykladowy@mail.com");            //:TODO : ustawić maila
            helper.setSubject(title);
            helper.setText(mailMessage);
        } catch (
                MessagingException e) {
            e.printStackTrace();
        } //TODO: ewentualnie można dodać blok finally w razie jakiegoś wyjątku/błędu etc
        javaMailSender.send(mail);
    }
}

