package library.services;

import com.google.common.collect.ImmutableList;
import library.enums.ActionDescription;
import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.services.modelservices.PaymentService;
import library.users.User;
import library.validators.mainValidators.AbstractValidator;
import library.validators.mainValidators.PaymentAmountValidator;
import library.validators.mainValidators.RentFifthBookValidator;
import library.validators.ZbiorczyWalidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    //TODO: przenieść do oddzielnego serwisu + refactoring/połąćzyć z walidatorem isBookLoanable
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
}
