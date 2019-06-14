package library.services.modelservices;

import library.enums.*;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.models.Payment;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.repositories.PaymentRepository;
import library.services.exceptions.ExceptionEmptyList;
import library.services.exceptions.ExceptionFactory;
import library.services.exceptions.TooManyResultsException;
import library.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {


    private final BookRepository bookRepository;
    private final BookStateRepository bookStateRepository;
    private final ActionRepository actionRepository;
    private final PaymentRepository paymentRepository;
    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final PaymentService paymentService;

    private Book addBook(Book book, User user) {
        Action newBookAction = actionService.addBook(book, user);
        bookStateService.newBook(newBookAction);
        return bookRepository.save(book);
    }

    /**
     * metoda zwraca BSEnuma-info o statusie ksiązki, czy jest wypożyczona/zwrócona/nowa/zniszczona etc
     */
    private BookStateEnum getBookStateEnum(Integer bookId) {
        return bookStateRepository.findBookStateByBook(bookId).getBookStateEnum();
    }

    private Book updateBook(Book book, Integer bookId, User user) {
        Book bookFromBase = bookRepository.getOne(bookId);
        if (bookFromBase == null) {
            log.info("Nie znaleziono takiej książki");
        } else {
            if (!book.getTitle().isEmpty()) {
                bookFromBase.setTitle(book.getTitle());
            }
            if (book.getAgeCategory() != bookFromBase.getAgeCategory()) {
                bookFromBase.setAgeCategory(book.getAgeCategory());
            }
            if (book.getAuthor() != null) {
                bookFromBase.setAuthor(book.getAuthor());
            }
            if (book.getCategory() != bookFromBase.getCategory()) {
                bookFromBase.setCategory(book.getCategory());
            }
            if (book.getReleaseDate().isEqual(bookFromBase.getReleaseDate())) {
                bookFromBase.setReleaseDate(book.getReleaseDate());
            }
            if (book.getStatus() != 0) {
                bookFromBase.setStatus(book.getStatus());
            }
        }
        actionService.updateBook(book, user);
        return bookRepository.save(bookFromBase);
    }

    public List<Book> sortedBooksByReleaseDate() {
        return bookRepository.sortedBooksByReleaseData();
    }

    public List<Book> sortedBooksByAddingDate() {
        return bookRepository.sortedBooksByAddingData();
    }

    //książki wydane w ciągu roku
    public List<Book> booksReleasedInPeriod() {
        return bookRepository.booksReleasedInPeriod(LocalDateTime.now().minusYears(1));
    }

    //książki dodane w ciągu miesiąca-nowości w bibliotece
    public List<Book> booksAddedInPeriod() {
        return bookRepository.booksAddedInPeriod(LocalDate.now().minusMonths(1));
    }


    public List<Book> booksByAgeCategory(AgeCategory ageCategory) {
        return bookRepository.findBookByAgeCategory(ageCategory);
    }

    /**
     * metoda zwraca pełną listę wypożyczeń użytkownika posortowaną według daty wypożczenia
     * można ewentualnie zmienić typ na listę książek
     */
    public List<BookState> showLoanHistory(User user) {
        return bookStateRepository.findBookStateByUser(user);
    }

    /**
     * metoda usuwa książkę ze zbioru dostępnych do wypożyczenia książek nadając jej status ZNISZCZONA
     * ustala też umowną karę dla użytkownika za zniszczenie książki
     */
    public void deleteBook(Book book, User user) {
        Action action = actionService.updateBook(book, user);
        BookState bookState = bookStateService.destroyBook(action);
        paymentService.destroyedBookPayment(bookState);
    }

    //TODO: metoda z exceptione x
    // x
    // x
    public Book latestLoanedBook(User user) { // PRZYKLAD
        List<Book> booksLoandedByUser = bookRepository.findLoanedBooksByUser(user);

        if (CollectionUtils.isEmpty(booksLoandedByUser)) {
            throw new ExceptionEmptyList("Aktualnie użytkownik nie ma wypożyczonych książek");
        }
        return booksLoandedByUser.stream().findFirst().get();
    }

    public Integer numberOfLoanedBooks(User user) {
        List<Book> booksLoandedByUser = bookRepository.findLoanedBooksByUser(user);
        final Integer MAX_LOANED_BOOK = 5;
        if (CollectionUtils.isEmpty(booksLoandedByUser)) {
            return 0;
        } else if (booksLoandedByUser.size() >= MAX_LOANED_BOOK) {
            throw new TooManyResultsException("Została przekroczona maksymalna ilość jednocześnie wypożyczonych książek ( "
                    + MAX_LOANED_BOOK + " sztuk).\nAktualnie posiadasz " + MAX_LOANED_BOOK + " wypożyczonych książek");
        }
        return booksLoandedByUser.size();
    }

    public Integer sumPagesForUser(User user) {
        Integer sumPagesForUser = bookRepository.sumPagesForUser(user);
        if (sumPagesForUser == null) {
            sumPagesForUser = 0;
            //ewentualnie można rzucić jakiegoś loga lub NullPointerException, ale czy jest sens?
        }
        return sumPagesForUser;
    }
}
