package library.services;

import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.users.User;
import library.validators.IsBookLoanable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    //warunek sprawdzający, czy książka istnieje (jest w bibliotece)
    private BookState isBookExisting(Integer bookId) {
        BookState bookState = bookStateRepository.findBookStateByBook(bookId);
        if (bookState == null) {
            log.info("Nie znalezionego książki o podanym ID");
        }
        return bookState;
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
        bookState.setDateOfLoan(LocalDate.now());
        bookState.setBookStateEnum(BookStateEnum.WYPOŻYCZONA);
        bookState.setDateOfReturn(LocalDate.now().plusDays(LOAN_PERIOD));
        bookState.setAction(action);
        bookStateRepository.save(bookState);
        return "Wypoczyłeś książkę pt. \"" + book.getTitle() + "\", dnia: " +
                LocalDate.now() + ". \nTermin zwrotu to:" + LocalDate.now().plusDays(30);
    }

    public void returnBook(Book book, User user /*czy tutaj user będzie potrzebny?*/) {
        Action action = new Action();
        action.setActionDescription("Zwrot książki");
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState = new BookState();
        bookState.setBook(book);
        bookState.setDateOfReturn(LocalDate.now());
        /*zrobić walidacje zwrotu książki, sprawdzić, czy użytkownik oddał w terminie 30 dni
        jeśli nie, to naliczyć karę*/
        bookState.setBookStateEnum(BookStateEnum.ZWRÓCONA);
        bookState.setAction(action);
        bookStateRepository.save(bookState);
    }
}
