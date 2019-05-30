package library.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import library.models.Book;
import library.models.Payment;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
import library.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class EmailService {
    private final RentService rentService;
    private final JavaMailSender javaMailSender;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    //TODO: zrobić maile dla: akcji związanych z książką-wypożyczenie, oddanie, zniszczenie, związanych z akcjami ewentualnymi zniszczeniami;
    //TODO: rejestracja, zbanowanie użytkownika, aktualizacja danych
    //TODO: można zrobić np. biuletyn co tydzień/miesiąc z informacją o nowych książkach (z walidacją wieku użytkownika)

    /**
     * wysyłanie maili, póki co dla konkretnego użytkownika
     * //:TODO do poprawienia/zrobić wariant dla różnych akcji
     */ //:TODO do przerobienia : metoda powinna przeszukać całą bazę w poszukiwaniu wszystkich aktualnych wypożyczeń i po nich sprawdzać daty oddania-w razie spełnienia warunków należy wysłać maila
    public void sendMailAboutRentBook(String title, Integer bookId, Integer userId) {
        Book book = bookRepository.getOne(bookId);
        User user = userRepository.getOne(userId);
        String mailMessage = rentService.rentBook(book, user);
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(userRepository.getOne(userId).getEmail());
            helper.setReplyTo("@przykladowy@mail.com");         //:TODO : ustawić maila
            helper.setFrom("@przykladowy@mail.com");            //:TODO : ustawić maila
            helper.setSubject(title);
            helper.setText(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }

    public void userRegistrationInfo(Integer userId) {
        String mailMessage = "Dziękujemy za rejestrację na naszym serwisie!\n" +
                "Przypominamy, że książki można wypożyczyć na 30 dni, natomiast w określonych warunkach można przedłużyć wypożyczenie o kolejne 30 dni\n";
        // + "Twoje dane to:"; //tu można albo wypisać dane usera (imię, nazwisko itd) albo ewentualnie w przyszłości dodać login/hasło etc
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(userRepository.getOne(userId).getEmail());
            helper.setReplyTo("@przykladowy@mail.com");         //:TODO : ustawić maila
            helper.setFrom("@przykladowy@mail.com");            //:TODO : ustawić maila
            helper.setSubject("Witamy w naszej bibliotece!");
            helper.setText(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void userUpdateInfo(Integer userId) {
        String mailMessage = "Twoje dane zostały zaktualizowane zgodnie z nowymi danymi!\n";
        // + "Twoje aktualne dane to:"; //tu można albo wypisać nowe dane usera (imię, nazwisko itd) albo ewentualnie w przyszłości dodać login/hasło etc
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(userRepository.getOne(userId).getEmail());
            helper.setReplyTo("@przykladowy@mail.com");         //:TODO : ustawić maila
            helper.setFrom("@przykladowy@mail.com");            //:TODO : ustawić maila
            helper.setSubject("Twoje dane zostały zaktualizowane!");
            helper.setText(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO: do zrobienia dwie wersje: za zniszczenie książki/oddanie po terminie
     */
    public void paymentInfo(Integer userId, Book book, Payment payment) {

        //TODO: string format- do poprawienia
        String mailMessage = "Zniszczyłeś książkę pt. \"" + book.getTitle() + "\".\nW związku z tym naliczyliśmy karę wynoszącą: "
                + payment.getAmount() + " złotych.\n";
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(userRepository.getOne(userId).getEmail());
            helper.setReplyTo("@przykladowy@mail.com");         //:TODO : ustawić maila
            helper.setFrom("@przykladowy@mail.com");            //:TODO : ustawić maila
            helper.setSubject("Informacje o płatności");
            helper.setText(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //TODO: !!! W przypadku użycia adnotacji @Scheduled, metody muszą bez parametrów!
    // Trzeba będzie rozwiązać inaczej niż przez parametr przekazywanie danych

    //TODO:do przemyślenia czy wiadomość ma być wysyłana codziennie w ostatnim tygodniu, czy np 2 razy-tydzień przed i ostatniego dnia

   /* @Scheduled //(cron = "0 0 21 * * *") //cron-w tej konfiguracji ustawia wykonanie zadania codziennie o godzinie 21
    public void returnBookReminder(String title, Integer bookId, Integer userId) {
        String mailMessage = "Przypominamy, że termin oddania książki pt.\"" + bookRepository.getOne(bookId).getTitle()
                + "\" to: " + bookStateRepository.findBookStateByBook(bookId).getDateOfReturn() + ". Prosimy o terminowy zwrot książki!";
        BookState bookState = bookStateRepository.findBookStateByBook(bookId);

        LocalDate today = LocalDate.now();

        //ta zmienna sprawdza, czy dzisiejszy dzień jest przed datą zwrotu książki TODO:trzeba
        boolean isBeforeReturn = today.isBefore(bookState.getDateOfReturn());
        //ta zmienna sprawdza, czy dzisiejszy dzień jest w ostatnim tygodniu wypożyczenia
        boolean isAfterUpdate = today.isAfter(bookState.getDateOfReturn().minusDays(7));

        //warunek sprawi, że maile będą wysłane tylko w ostatnim tygodniu wypożyczenia
        if (isBeforeReturn && isAfterUpdate) {
            MimeMessage mail = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mail, true);
                helper.setTo(userRepository.getOne(userId).getEmail());
                helper.setReplyTo("@przykladowy@mail.com");         //:TODO : ustawić maila
                helper.setFrom("@przykladowy@mail.com");            //:TODO : ustawić maila
                helper.setSubject(title);
                helper.setText(mailMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            javaMailSender.send(mail);
        } else {
            log.info("Nie wysłano żadnej wiadomości"); //
        }
    }

    @Scheduled //(cron = " 0 12 1 * *") //newsletter będzie wysyłany pierwszego dnia miesiąca o godzinie 12
    public void newsletter(Integer userId) {

        *
     *zrobić nową klasę newsletter, z walidacjami, która:
     *będzie zwracać różne wiadomości dla różnych użytkowników, tzn:
     *dla dorosłych i dla dzieci będzie oddzielny mail, z listą nowych książek(z walidacją AgeCategory)
                * można też zwrócić listę 5 najpopularniejszych książek z poprzedniego miesiąca lub coś w tym stylu

        String mailMessage = "";
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(userRepository.getOne(userId).getEmail());
            helper.setReplyTo("@przykladowy@mail.com");         //:TODO : ustawić maila
            helper.setFrom("@przykladowy@mail.com");            //:TODO : ustawić maila
            helper.setSubject("Newsletter");
            helper.setText(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }*/

}
