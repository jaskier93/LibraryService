package library.services;

import library.enums.ActionDescription;
import library.enums.BookStateEnum;
import library.enums.StatusRekordu;
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
import library.validators.ZbiorczyWalidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentService extends MotherOfServices {

    public static final Integer LOAN_PERIOD = 30;

    private final BookRepository bookRepository;
    private final BookStateRepository bookStateRepository;
    private final ActionRepository actionRepository;
    private final ZbiorczyWalidator zbiorczyWalidator;
    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final PaymentService paymentService;

    @Override
    public void DoSomethingWithBook(User user, Book book) {
    }

    @Override
    public void cancel(User user, Book book) {
    }

    @Override
    public void corection(User user, Book book) {
    }


    //warunek sprawdzający, czy książka istnieje (jest w bibliotece) i czy nie jest zniszczona
    private Book isBookExisting(Integer bookId) {
        BookState bookState = bookStateRepository.findBookStateByBook(bookId);
        if (bookState == null || bookState.getBookStateEnum() == BookStateEnum.ZNISZCZONA) {
            log.info("Nie znalezionego książki o podanym ID");
        }
        return bookState.getBook();
    }

    /**
     * wypożyczanie książki
     * TODO: do przemyślenia :czy na potrzeby Mail Schedulera metody powinny zwracać Stringa z odpowiednim komunikatem?
     */

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
                "Termin zwrotu książki to: " + LocalDate.now().plusDays(30);
    }
}
