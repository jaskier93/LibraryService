package library.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import library.models.Book;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private RentService rentService;
    private JavaMailSender javaMailSender;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Autowired
    public EmailService(RentService rentService, JavaMailSender javaMailSender, BookRepository bookRepository, UserRepository userRepository) {
        this.rentService = rentService;
        this.javaMailSender = javaMailSender;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    /**
     * wysyłanie maili
     * //:TODO do poprawienia/zrobić wariant dla różnych akcji
     */
    public void send(String email, String title, Integer bookId, Integer userId) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(email);
            helper.setReplyTo("@przykladowy@mail.com");         //:TODO : ustawić maila
            helper.setFrom("@przykladowy@mail.com");            //:TODO : ustawić maila
            helper.setSubject(title);
            helper.setText(rentService.rentBook(bookRepository.getOne(bookId), userRepository.getOne(userId)));
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally { //:TODO ?
        }
        javaMailSender.send(mail);
    }
}
