package library.services;

import library.enums.ActionDescription;
import library.enums.BookStateEnum;
import library.enums.StatusRekordu;
import library.models.*;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.services.exceptions.ExceptionEmptyList;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.services.modelservices.PaymentService;
import library.validators.ZbiorczyWalidator;
import library.validators.mainValidators.AbstractValidator;
import library.validators.mainValidators.ReturnBookVaildator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service

public class ReturnBookService extends AbstractService {

    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final PaymentService paymentService;
    private final ReturnBookVaildator returnBookVaildator;
    private final ActionRepository actionRepository;
    private final BookStateRepository bookStateRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReturnBookService(ZbiorczyWalidator zbiorczyWalidator, ActionService actionService, BookStateService bookStateService, PaymentService paymentService,
                             ReturnBookVaildator returnBookVaildator, ActionRepository actionRepository, BookStateRepository bookStateRepository, BookRepository bookRepository) {
        super(zbiorczyWalidator);
        this.actionService = actionService;
        this.bookStateService = bookStateService;
        this.paymentService = paymentService;
        this.returnBookVaildator = returnBookVaildator;
        this.actionRepository = actionRepository;
        this.bookStateRepository = bookStateRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public void mainAction(User user, Book book) {
        //TODO:dodać walidację daty zwrotu książki, if true-tworzymy dwie akcje, else-tworzona jedna (prawidłowy termin zwrotu)
        Action expiredAction = actionService.expiredLoan(book, user);
        Action returnBookAction = actionService.returnBook(book, user);
        BookState returnBookState = bookStateService.returnBook(returnBookAction);
        /*zrobić walidacje zwrotu książki, sprawdzić, czy użytkownik oddał w terminie 30 dni
        jeśli nie, to naliczyć karę */
        Payment expiredLoanPayment = paymentService.expiredLoan(returnBookState);
        //TODO:ewentualnie dodać walidację (jeśli potrzebna)-czy użytkownik od razu płaci karę
        Action paymentAction = actionService.paymentInfo(book, user);
        Payment updatedExpiredLoanPayment = paymentService.updatePayment(expiredLoanPayment.getId());
    }

    @Override
    public void cancelAction(User user, Book book) {
        Action actionFromBase = actionRepository.findNewestAction(user, ActionDescription.ZWROT, LocalDateTime.now().minusDays(3)).get(0);
        BookState bookStateFromBase = bookStateRepository.findNewestBookState(user, ActionDescription.ZWROT).get(0);
        if (user.getId().equals(actionFromBase.getId()) && book.getId().equals(bookStateFromBase.getId())) {
            actionFromBase.setStatusRekordu(StatusRekordu.HISTORY);
            bookStateFromBase.setStatusRekordu(StatusRekordu.HISTORY);
            bookStateFromBase.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
            actionRepository.save(actionFromBase);
            bookStateRepository.save(bookStateFromBase);
            bookRepository.save(book);
        } else {
            throw new ExceptionEmptyList(" Nie odnaleziono akcji/książki/użytkownika. ");
        }
    }

    @Override
    public List<AbstractValidator> getValidators() {
        return null;
    }

    @Override
    public List<ActionDescription> allowedActions() {
        return null;
    }
}
