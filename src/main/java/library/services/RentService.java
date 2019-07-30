package library.services;

import com.google.common.collect.ImmutableList;
import library.enums.ActionDescription;
import library.enums.BookStateEnum;
import library.enums.StatusRekordu;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.services.exceptions.ExceptionEmptyList;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.services.modelservices.PaymentService;
import library.models.User;
import library.validators.mainValidators.AbstractValidator;
import library.validators.mainValidators.BookAmountValidator;
import library.validators.mainValidators.PaymentAmountValidator;
import library.validators.mainValidators.RentFifthBookValidator;
import library.validators.ZbiorczyWalidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class RentService extends AbstractService {

    public static final Integer LOAN_PERIOD = 30;

    private final BookRepository bookRepository;
    private final BookStateRepository bookStateRepository;
    private final ActionRepository actionRepository;
    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final PaymentService paymentService;
    private final PaymentAmountValidator paymentAmountValidator;
    private final RentFifthBookValidator rentFifthBookValidator;
    private final BookAmountValidator bookAmountValidator;

    @Autowired
    public RentService(ZbiorczyWalidator zbiorczyWalidator, BookRepository bookRepository, BookStateRepository bookStateRepository, ActionRepository actionRepository,
                       ActionService actionService, BookStateService bookStateService, PaymentService paymentService, PaymentAmountValidator paymentAmountValidator,
                       RentFifthBookValidator rentFifthBookValidator, BookAmountValidator bookAmountValidator) {
        super(zbiorczyWalidator);
        this.bookRepository = bookRepository;
        this.bookStateRepository = bookStateRepository;
        this.actionRepository = actionRepository;
        this.actionService = actionService;
        this.bookStateService = bookStateService;
        this.paymentService = paymentService;
        this.paymentAmountValidator = paymentAmountValidator;
        this.rentFifthBookValidator = rentFifthBookValidator;
        this.bookAmountValidator = bookAmountValidator;
    }

    //RM
    // Problem: Wydaje mi się że tutaj mamy design smell.
    // Solucja: Wydaje mi się że BookService powinen mieć metode Borrow, która ustalała by akcje na loanBook oraz robiła prolongation. Klient wołający
    // taką metodę nie chce "wiedzieć" co trzeba zrobić aby książkę wypożyczyć (czyli jaki zestaw metod musi zostać wykonany w tym celu). Władowanie tego do Book,
    // rozwiązało by problem. - mogę się tu jednak mylić bo jeszcze nie ogarnąłem całej architektury i aplikacji i piszę na gorąco co widzę :D.

    //Dodatkowo metoda jest użyta w [EmailService] -> tam też jest mój komentarz bo przeróbka tam też będzie potrzebna. Zwróć uwagę na niebezpieczeństwo takiego wywołania
    // EmailService.sendMailAboutRentBook woła RentService.rentBook i ze zwróconą wiadomościa wysyła maila - co jeśli nie uda się wypożyczyć książki? Mail pójdzie a książki
    // ni ma, i cyk wsciekły Janusz dzwoni na infolinie i burdę robi bo już dzieciaczkowi obiecał i on nie będzie mu teraz tłumaczył że błędy w sofcie są.

    //wypożyczanie książek
/*    String rentBook(Book book, User user) {
        boolean fourthBook = bookAmountValidator.validator(user);
        boolean fifthBook = rentFifthBookValidator.validator(user);

        if (fifthBook || fourthBook)  //po dodaniu listy walidacji może być if(fifthBook ||  zbiorczyWalidator.checkIt(getValidators(), user))
        {
            Action action = actionService.loanBook(book, user);
            BookState bookState = bookStateService.prolongation(action);
            return "Wypożyczyłeś książkę pt. \"" + book.getTitle() + "\", dnia: " +
                    bookState.getDateOfLoan() + ". \nTermin zwrotu to:" + bookState.getDateOfReturn();
        }
        return "Nie spełniłeś warunków, by wypożyczyć następną książkę";
    }*/

    @Override
    public void mainAction(User user, Book book) {
        boolean fourthBook = bookAmountValidator.validator(user);
        boolean fifthBook = rentFifthBookValidator.validator(user);

        if (fifthBook || fourthBook)  //po dodaniu listy walidacji może być if(fifthBook ||  zbiorczyWalidator.checkIt(getValidators(), user))
        {
            Action action = actionService.loanBook(book, user);
            BookState bookState = bookStateService.prolongation(action);
        }
    }

    @Override
    public void cancelAction(User user, Book book) {
        Action actionFromBase = actionRepository.findNewestAction(user, ActionDescription.WYPOZYCZENIE, LocalDateTime.now().minusDays(3)).get(0);
        BookState bookStateFromBase = bookStateRepository.findNewestBookState(user, ActionDescription.WYPOZYCZENIE).get(0);
        if (user.getId().equals(actionFromBase.getId()) && book.getId().equals(bookStateFromBase.getId())) {
            actionFromBase.setStatusRekordu(StatusRekordu.HISTORY);
            bookStateFromBase.setStatusRekordu(StatusRekordu.HISTORY);
            bookStateFromBase.setBookStateEnum(BookStateEnum.ZWROCONA);
            actionRepository.save(actionFromBase);
            bookStateRepository.save(bookStateFromBase);
            bookRepository.save(book);
        } else {
            throw new ExceptionEmptyList(" Nie odnaleziono akcji/książki/użytkownika. ");
        }
    }

    @Override
    public List<AbstractValidator> getValidators() {
        return ImmutableList.of(bookAmountValidator, rentFifthBookValidator, paymentAmountValidator);
    }

    @Override
    public List<ActionDescription> allowedActions() {
        return ImmutableList.of(ActionDescription.WYPOZYCZENIE);
    }

}
