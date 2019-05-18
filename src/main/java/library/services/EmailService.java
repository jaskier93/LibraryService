package library.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import library.models.Book;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.repositories.UserRepository;
import library.users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@EnableScheduling
public class EmailService {
    private RentService rentService;
    private JavaMailSender javaMailSender;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BookStateRepository bookStateRepository;

    @Autowired
    public EmailService(RentService rentService, JavaMailSender javaMailSender, BookRepository bookRepository, UserRepository userRepository, BookStateRepository bookStateRepository) {
        this.rentService = rentService;
        this.javaMailSender = javaMailSender;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookStateRepository = bookStateRepository;
    }

    /**
     * wysyłanie maili, póki co dla konkretnego użytkownika
     * //:TODO do poprawienia/zrobić wariant dla różnych akcji
     */ //:TODO do przerobienia : metoda powinna przeszukać całą bazę w poszukiwaniu wszystkich aktualnych wypożyczeń i po nich sprawdzać daty oddania-w razie spełnienia warunków należy wysłać maila
    @Scheduled(cron = "0 0 21 * * *") //cron-w tej konfiguracji ustawia wykonanie zadania codziennie o godzinie 21
    public void send(String title, Integer bookId, Integer userId) {
        //tak właściwie tego rodzaju warunek będzie przy zbliżającej się dacie zwrotu-np codziennie wysyłanie przypomnienia z prośbą zwrotu ksiązki (np ostani tydzień)
        if (bookStateRepository.findBookStateByBook(bookId).getDateOfReturn().equals(LocalDate.now().plusDays(30))) {
            MimeMessage mail = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mail, true);
                helper.setTo(userRepository.getOne(userId).getEmail());
                helper.setReplyTo("@przykladowy@mail.com");         //:TODO : ustawić maila
                helper.setFrom("@przykladowy@mail.com");            //:TODO : ustawić maila
                helper.setSubject(title);
                helper.setText(rentService.rentBook(bookRepository.getOne(bookId), userRepository.getOne(userId)));
            } catch (MessagingException e) {
                e.printStackTrace();
            } finally { //:TODO ?
            }
            javaMailSender.send(mail);
        } else {
            log.info("Nie wysłano żadnej wiadomości"); //
        }
    }
}
