package library.services;

import library.enums.ActionDescription;
import library.models.Book;
import library.models.Payment;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.services.modelservices.PaymentService;
import library.users.User;
import library.validators.ZbiorczyWalidator;
import library.validators.mainValidators.AbstractValidator;
import library.validators.mainValidators.ReturnBookVaildator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class ReturnBookService  extends AbstractService{

    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final PaymentService paymentService;
    private final ReturnBookVaildator returnBookVaildator;

    @Autowired
    public ReturnBookService(ZbiorczyWalidator zbiorczyWalidator, ActionService actionService, BookStateService bookStateService,
                             PaymentService paymentService, ReturnBookVaildator returnBookVaildator) {
        super(zbiorczyWalidator);
        this.actionService = actionService;
        this.bookStateService = bookStateService;
        this.paymentService = paymentService;
        this.returnBookVaildator = returnBookVaildator;
    }


    //czy jest sens wysyłać maila w przypadku zwrotu książki?
    public void returnBook(Book book, User user) {

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

    @Override
    public void DoSomethingWithBook(User user, Book book) {

    }

    @Override
    public void cancel(User user, Book book) {

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
