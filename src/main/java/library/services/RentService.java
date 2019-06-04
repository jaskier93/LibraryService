package library.services;

import com.google.common.collect.ImmutableList;
import library.enums.ActionDescription;
import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.models.Payment;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.services.modelservices.PaymentService;
import library.users.User;
import library.validators.AbstractValidator;
import library.validators.PaymentAmountValidator;
import library.validators.RentFifthBookValidator;
import library.validators.ZbiorczyWalidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class RentService extends MotherOfServices {

    public static final Integer LOAN_PERIOD = 30;

    private final BookRepository bookRepository;
    private final BookStateRepository bookStateRepository;
    private final ActionRepository actionRepository;
    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final PaymentService paymentService;
    private final PaymentAmountValidator paymentAmountValidator;
    private final RentFifthBookValidator rentFifthBookValidator;

    @Autowired
    public RentService(ZbiorczyWalidator zbiorczyWalidator, BookRepository bookRepository, BookStateRepository bookStateRepository, ActionRepository actionRepository, ActionService actionService, BookStateService bookStateService, PaymentService paymentService, PaymentAmountValidator paymentAmountValidator, RentFifthBookValidator rentFifthBookValidator) {
        super(zbiorczyWalidator);
        this.bookRepository = bookRepository;
        this.bookStateRepository = bookStateRepository;
        this.actionRepository = actionRepository;
        this.actionService = actionService;
        this.bookStateService = bookStateService;
        this.paymentService = paymentService;
        this.paymentAmountValidator = paymentAmountValidator;
        this.rentFifthBookValidator = rentFifthBookValidator;
    }

    @Override
    public void DoSomethingWithBook(User user, Book book) {

    }

    @Override
    public void cancel(User user, Book book) {

    }

    @Override
    public List<AbstractValidator> getValidators() {
        return ImmutableList.of(rentFifthBookValidator, paymentAmountValidator);
    }

    @Override
    public List<ActionDescription> allowedActions() {
        return ImmutableList.of(ActionDescription.WYPOZYCZENIE);
    }

    //RM:
    // Problem: Nazwa metody wprowadza w błąd, dodatkowo złamana reguła SOLID -> osoba która chciała by użyć tej metody może być zdezorientowana - nazwa wskazuje na sprawdzenie
    //czy w bazie istnieje książka, natomiast metoda dodatkowo sprawdza stan książki. 
    //Solucja: W zależności od potrzeby biznesowej - jeśli książka, która jest zniszczona nie może zostać wypożyczona to rozbiłbym te metode na IsBookExisting oraz
    // coś w stylu IsBookRentable - wtedy klient wołający te metodę ma jasną sprawę - może sprawdzić czy książka w ogóle istnieje lub sprawdzić czy ją się da wypożyczyć. 
    // Jeśli była by potrzeba to stworzyłbym jeszcze IsBookBroken ale tutaj warto pamiętać o YAGNI.

    //warunek sprawdzający, czy książka istnieje (jest w bibliotece) i czy nie jest zniszczona
    private Book isBookExisting(Integer bookId) {
        BookState bookState = bookStateRepository.findBookStateByBook(bookId);
        Book book = bookState.getBook();
        if (bookState == null) {
            log.info("Nie znalezionego książki o podanym ID");
        }
        if (bookState.getBookStateEnum() == BookStateEnum.ZNISZCZONA) {
            log.info("Ta książka jest zniszczona");
        }
        return book;
    }

    /**
     * wypożyczanie książki
     * TODO: do przemyślenia :czy na potrzeby Mail Schedulera metody powinny zwracać Stringa z odpowiednim komunikatem?
     */

    //RM
    // Problem: Wydaje mi się że tutaj mamy design smell. 
    // Solucja: Wydaje mi się że BookService powinen mieć metode Borrow, która ustalała by akcje na loanBook oraz robiła prolongation. Klient wołający
    // taką metodę nie chce "wiedzieć" co trzeba zrobić aby książkę wypożyczyć (czyli jaki zestaw metod musi zostać wykonany w tym celu). Władowanie tego do Book,
    // rozwiązało by problem. - mogę się tu jednak mylić bo jeszcze nie ogarnąłem całej architektury i aplikacji i piszę na gorąco co widzę :D.

    //Dodatkowo metoda jest użyta w [EmailService] -> tam też jest mój komentarz bo przeróbka tam też będzie potrzebna. Zwróć uwagę na niebezpieczeństwo takiego wywołania
    // EmailService.sendMailAboutRentBook woła RentService.rentBook i ze zwróconą wiadomościa wysyła maila - co jeśli nie uda się wypożyczyć książki? Mail pójdzie a książki 
    // ni ma, i cyk wsciekły Janusz dzwoni na infolinie i burdę robi bo już dzieciaczkowi obiecał i on nie będzie mu teraz tłumaczył że błędy w sofcie są.
    String rentBook(Book book, User user) {
        Action action = actionService.loanBook(book, user);
        BookState bookState = bookStateService.prolongation(action);
        return "Wypoczyłeś książkę pt. \"" + book.getTitle() + "\", dnia: " +
                bookState.getDateOfLoan() + ". \nTermin zwrotu to:" + bookState.getDateOfReturn();
    }

    //czy jest sens wysyłać maila w przypadku zwrotu książki?
    public void returnBook(Book book, User user /*czy tutaj user będzie potrzebny?*/) {

        //TODO:dodać walidację daty zwrotu książki, if true-tworzymy dwie akcje, else-tworzona jedna (prawidłowy termin zwrotu)
        actionService.expiredLoan(book, user);
        actionService.returnBook(book, user);
        bookStateService.returnBook(actionService.returnBook(book, user));
        /*zrobić walidacje zwrotu książki, sprawdzić, czy użytkownik oddał w terminie 30 dni
        jeśli nie, to naliczyć karę*/
        Payment expiredLoanPayment = paymentService.expiredLoan(bookStateService.returnBook(actionService.returnBook(book, user)));
        //TODO:ewentualnie dodać walidację (jeśli potrzebna)-czy użytkownik od razu płaci karę
        actionService.paymentInfo(book, user);
        paymentService.updatePayment(expiredLoanPayment.getId());

    }

    /**
     * TODO: dorobić walidację, czy użytkownik już wcześniej przedłużał te wypożyczenie (będzie można przedłużyć tylko raz)
     * oraz czy nie próbuje przedłużyć po dacie zwrotu
     */
    public String loanBookProlongation(Book book, User user) {
        //najpierw wywołać metodę walidującą z ProlongationValidator
        actionService.prolongation(book, user);
        bookStateService.prolongation(actionService.prolongation(book, user));
        return "Przedłużyłeś wypożyczenie książki pt.\"" + book.getTitle() + "\"." +
                "Termin zwrotu książki to: " + LocalDate.now().plusDays(LOAN_PERIOD);
    }
}
