package library.services;

import library.enums.ActionDescription;
import library.models.Book;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.users.User;
import library.validators.ZbiorczyWalidator;
import library.validators.mainValidators.AbstractValidator;
import library.validators.mainValidators.ProlongationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProlongationService extends AbstractService {

    private static final Integer LOAN_PERIOD = 30;

    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final ProlongationValidator prolongationValidator;

    @Autowired
    public ProlongationService(ZbiorczyWalidator zbiorczyWalidator, ActionService actionService, BookStateService bookStateService, ProlongationValidator prolongationValidator) {
        super(zbiorczyWalidator);
        this.actionService = actionService;
        this.bookStateService = bookStateService;
        this.prolongationValidator = prolongationValidator;
    }




    /**
     * TODO: dorobić walidację, czy użytkownik już wcześniej przedłużał te wypożyczenie (będzie można przedłużyć tylko raz)
     * oraz czy nie próbuje przedłużyć po dacie zwrotu
     */
    public String loanBookProlongation(Book book, User user) {
        //najpierw wywołać metodę walidującą z ProlongationValidator
        if (prolongationValidator.validator(user)) //lista walidacji
        {
            actionService.prolongation(book, user);
            bookStateService.prolongation(actionService.prolongation(book, user));
            return "Przedłużyłeś wypożyczenie książki pt.\"" + book.getTitle() + "\"." +
                    "Termin zwrotu książki to: " + LocalDate.now().plusDays(LOAN_PERIOD);
        }
        return "Nie udało się przedłużyć książki";
    }


    @Override
    public void doSomethingWithBook(User user, Book book) {

    }

    @Override
    public void cancel(User user, Book book) {

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
