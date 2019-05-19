package library.services;

import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RentService {
    public static final Integer LOAN_PERIOD = 30;

    private BookRepository bookRepository;
    private BookStateRepository bookStateRepository;
    private ActionRepository actionRepository;

    @Autowired
    public RentService(BookRepository bookRepository, BookStateRepository bookStateRepository, ActionRepository actionRepository) {
        this.bookRepository = bookRepository;
        this.bookStateRepository = bookStateRepository;
        this.actionRepository = actionRepository;
    }

    //warunek sprawdzający, czy książka istnieje (jest w bibliotece) i czy nie jest zniszczona
    private Book isBookExisting(Integer bookId) {
        BookState bookState = bookStateRepository.findBookStateByBook(bookId);
        if (bookState == null || bookState.getBookStateEnum() == BookStateEnum.ZNISZCZONA) {
            log.info("Nie znalezionego książki o podanym ID");
        }
        return bookStateRepository.findBookStateByBook(bookId).getBook();
    }

    /**
     * wypożyczanie książki
     * TODO: do przemyślenia :czy na potrzeby Mail Schedulera metody powinny zwracać Stringa z odpowiednim komunikatem?
     */

    public String rentBook(Book book, User user) {
        Action action = new Action();
        action.setActionDescription("Wypożyczenie");
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState = new BookState();
        bookState.setBook(book);
        bookState.setBookStateEnum(BookStateEnum.WYPOŻYCZONA);
        bookState.setAction(action);
        bookStateRepository.save(bookState);
        return "Wypoczyłeś książkę pt. \"" + book.getTitle() + "\", dnia: " +
                bookState.getDateOfLoan() + ". \nTermin zwrotu to:" + bookState.getDateOfReturn();
    }

    public void returnBook(Book book, User user /*czy tutaj user będzie potrzebny?*/) {
        Action action = new Action();
        action.setActionDescription("Zwrot książki");
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState = new BookState();
        bookState.setBook(book);
        /*zrobić walidacje zwrotu książki, sprawdzić, czy użytkownik oddał w terminie 30 dni
        jeśli nie, to naliczyć karę*/
        bookState.setBookStateEnum(BookStateEnum.ZWRÓCONA);
        bookState.setAction(action);
        bookStateRepository.save(bookState);
    }

    public void loanBookProlongation(Book book, User user) {
        //najpierw wywołać metodę walidującą z ProlongationValidator
        Action action = new Action();
        action.setActionDescription("Przedłużenie wypożyczenia książki");
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState = new BookState();
        bookState.setBook(book);
        bookState.setBookStateEnum(BookStateEnum.WYPOŻYCZONA);
        bookState.setAction(action);
        bookStateRepository.save(bookState);
    }
}
