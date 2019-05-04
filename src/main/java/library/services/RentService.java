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

    //warunek sprawdzający, czy książka ma status nowa/zwrócona-czy można ją wypożyczyć
    private boolean isBookAbleToLoan(Book book) {
        BookState bookState2 = isBookExisting(book.getId());
        if ((bookState2.getBookStateEnum() == BookStateEnum.ZWRÓCONA) ||
                ((bookState2.getBookStateEnum() == BookStateEnum.NOWA))) {
            log.info("Możesz wypożyczyć tą książkę");

        }
        log.info("Podana książka jest wypożczyona lub zniszczona, nie możesz jej wypożyczyć. ");
        return false;
    }

    //warunek sprawdzający, czy książka istnieje (jest w bibliotece)
    private BookState isBookExisting(Integer bookId) {
        BookState bookState = bookStateRepository.findBookStateByBook(bookId);
        if (bookState == null) {
            log.info("Nie znalezionego książki o podanym ID");
        }
        return bookState;
    }

    //wypożyczanie książki
    public void rentBook(Book book, User user) {
        Action action = new Action();
        action.setActionDescription("Wypożyczenie");
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState3 = new BookState();
        bookState3.setBook(book);
        bookState3.setDateOfLoan(LocalDate.now());
        bookState3.setBookStateEnum(BookStateEnum.WYPOŻYCZONA);
        bookState3.setDateOfReturn(LocalDate.now().plusDays(LOAN_PERIOD));
        bookState3.setAction(action);
        bookStateRepository.save(bookState3);
    }

    public void returnBook(Book book, User user /*czy tutaj user będzie potrzebny?*/) {
        Action action = new Action();
        action.setActionDescription("Zwrot książki");
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState4 = new BookState();
        bookState4.setBook(book);
        bookState4.setDateOfReturn(LocalDate.now());
        /*zrobić walidacje zwrotu książki, sprawdzić, czy użytkownik oddał w terminie 30 dni
        jeśli nie, to naliczyć karę*/
        bookState4.setBookStateEnum(BookStateEnum.ZWRÓCONA);
        bookState4.setAction(action);
        bookStateRepository.save(bookState4);
    }


}
