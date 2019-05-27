package library.services;

import library.enums.ActionDescription;
import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.users.User;
import library.validators.AbstractValidator;
import library.validators.ZbiorczyWalidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RentService extends MotherOfServices {

    public static final Integer LOAN_PERIOD = 30;

    private final BookRepository bookRepository;
    private final BookStateRepository bookStateRepository;
    private final ActionRepository actionRepository;
    private final ZbiorczyWalidator zbiorczyWalidator;

    @Autowired
    public RentService(BookRepository bookRepository, BookStateRepository bookStateRepository, ActionRepository actionRepository, ZbiorczyWalidator zbiorczyWalidator) {
        this.bookRepository = bookRepository;
        this.bookStateRepository = bookStateRepository;
        this.actionRepository = actionRepository;
        this.zbiorczyWalidator = zbiorczyWalidator;
    }

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

    public String rentBook(Book book, User user) {
        Action action = new Action();
        action.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState = new BookState();
        bookState.setBook(book);
        bookState.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookState.setAction(action);
        bookStateRepository.save(bookState);
        return "Wypoczyłeś książkę pt. \"" + book.getTitle() + "\", dnia: " +
                bookState.getDateOfLoan() + ". \nTermin zwrotu to:" + bookState.getDateOfReturn();
    }

    //czy jest sens wysyłać maila w przypadku zwrotu książki?
    public void returnBook(Book book, User user /*czy tutaj user będzie potrzebny?*/) {

        /**
         * tworzone będą dwie akcje w przypadku zwrotu książki po terminie, w przypadku prawidłowego-tylko jedna
         */
        //TODO:dodać walidację daty zwrotu książki, if true-tworzymy dwie akcje, else-tworzona jedna (prawidłowy termin zwrotu)
        Action actionExpiredLoan = new Action();
        actionExpiredLoan.setActionDescription(ActionDescription.PRZETERMINOWANIE);
        actionExpiredLoan.setBook(book);
        actionExpiredLoan.setUser(user);
        actionRepository.save(actionExpiredLoan);

        Action actionReturnBook = new Action();
        actionReturnBook.setActionDescription(ActionDescription.ZWROT);
        actionReturnBook.setBook(book);
        actionReturnBook.setUser(user);
        actionRepository.save(actionReturnBook);

        BookState bookState = new BookState();
        bookState.setBook(book);
        /*zrobić walidacje zwrotu książki, sprawdzić, czy użytkownik oddał w terminie 30 dni
        jeśli nie, to naliczyć karę*/
        bookState.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookState.setAction(actionReturnBook);
        bookStateRepository.save(bookState);
    }

    /**
     * TODO: dorobić walidację, czy użytkownik już wcześniej przedłużał te wypożyczenie (będzie można przedłużyć tylko raz)
     * oraz czy nie próbuje przedłużyć po dacie zwrotu
     */
    public String loanBookProlongation(Book book, User user) {
        //najpierw wywołać metodę walidującą z ProlongationValidator
        Action action = new Action();
        action.setActionDescription(ActionDescription.PRZEDLUZENIE);
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState = new BookState();
        bookState.setBook(book);
        bookState.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookState.setAction(action);
        bookState.setDateOfReturn(LocalDate.now().plusDays(30)); //nowa data zwrotu
        bookState.setDateOfUpdate(LocalDate.now());
        bookStateRepository.save(bookState);
        return "Przedłużyłeś wypożyczenie książki pt.\"" + book.getTitle() + "\"." +
                "Termin zwrotu książki to: " + bookState.getDateOfUpdate();
    }
}
