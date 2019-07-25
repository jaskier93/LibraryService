package library.services;

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
import library.models.User;
import library.validators.ZbiorczyWalidator;
import library.validators.mainValidators.AbstractValidator;
import library.validators.mainValidators.ProlongationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ProlongationService extends AbstractService {

    private static final Integer LOAN_PERIOD = 30;

    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final ProlongationValidator prolongationValidator;
    private final ActionRepository actionRepository;
    private final BookStateRepository bookStateRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ProlongationService(ZbiorczyWalidator zbiorczyWalidator, ActionService actionService, BookStateService bookStateService, ProlongationValidator prolongationValidator,
                               ActionRepository actionRepository, BookStateRepository bookStateRepository, BookRepository bookRepository) {
        super(zbiorczyWalidator);
        this.actionService = actionService;
        this.bookStateService = bookStateService;
        this.prolongationValidator = prolongationValidator;
        this.actionRepository = actionRepository;
        this.bookStateRepository = bookStateRepository;
        this.bookRepository = bookRepository;
    }

/*    public String loanBookProlongation(Book book, User user) {
        //najpierw wywołać metodę walidującą z ProlongationValidator
        if (prolongationValidator.validator(user)) //lista walidacji
        {
            actionService.prolongation(book, user);
            bookStateService.prolongation(actionService.prolongation(book, user));
            return "Przedłużyłeś wypożyczenie książki pt.\"" + book.getTitle() + "\"." +
                    "Termin zwrotu książki to: " + LocalDate.now().plusDays(LOAN_PERIOD);
        }
        return "Nie udało się przedłużyć książki";
    }*/


    /**
     * TODO: dorobić walidację, czy użytkownik już wcześniej przedłużał te wypożyczenie (będzie można przedłużyć tylko raz)
     * oraz czy nie próbuje przedłużyć po dacie zwrotu
     */
    @Override
    public void mainAction(User user, Book book) {
        if (prolongationValidator.validator(user)) //lista walidacji
        {
            actionService.prolongation(book, user);
            bookStateService.prolongation(actionService.prolongation(book, user));
        }
    }

    @Override
    public void cancelAction(User user, Book book) {
        Action actionFromBase = actionRepository.findNewestAction(user).get(0);
        BookState bookStateFromBase = bookStateRepository.findNewestBookState(user).get(0);
        if (user == actionFromBase.getUser() && book == bookStateFromBase.getBook()) {
            actionFromBase.setStatusRekordu(StatusRekordu.HISTORY);
            bookStateFromBase.setStatusRekordu(StatusRekordu.HISTORY);
            bookStateFromBase.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
            bookRepository.save(book);
        } else {
            throw new ExceptionEmptyList("Wystąpił błąd"); //wstawić prezcyzyjny komunikat
        }
    }

    @Override
    public List<AbstractValidator> getValidators() {
        //    getValidators().add(prolongationValidator);
        return getValidators();
    }

    @Override
    public List<ActionDescription> allowedActions() {
        return null;
    }
}
