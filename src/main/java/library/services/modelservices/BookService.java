package library.services.modelservices;

import library.enums.*;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.exceptions.ExceptionEmptyList;
import library.exceptions.TooManyResultsException;
import library.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookService {

    private static final Integer MAX_LOANED_BOOK = 5;

    private final BookRepository bookRepository;
    private final BookStateRepository bookStateRepository;
    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final PaymentService paymentService;

    @Autowired
    public BookService(BookRepository bookRepository, BookStateRepository bookStateRepository, ActionService actionService, BookStateService bookStateService, PaymentService paymentService) {
        this.bookRepository = bookRepository;
        this.bookStateRepository = bookStateRepository;
        this.actionService = actionService;
        this.bookStateService = bookStateService;
        this.paymentService = paymentService;
    }

    public Book addBook(Book book, User admin) {
        Action newBookAction = actionService.createAction(book, admin, ActionDescription.NOWOSC);
        bookStateService.createBookState(newBookAction, BookStateEnum.NOWA);
        return bookRepository.save(book);
    }

    /**
     * metoda zwraca BSEnuma-info o statusie ksiązki, czy jest wypożyczona/zwrócona/nowa/zniszczona etc
     */
    public BookStateEnum getBookStateEnum(Integer bookId) {
        return bookStateRepository.findBookStateByBook(bookId).getBookStateEnum();
    }

    public Book updateBook(Book book, Integer bookId, User user) {
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
        actionService.createAction(book, user, ActionDescription.AKTUALIZACJA);
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

    public List<Book> booksByCategory(Category category) {
        return bookRepository.findBookByCategory(category);
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
        Action action = actionService.createAction(book, user, ActionDescription.ZNISZCZENIE);
        BookState bookState = bookStateService.createBookState(action, BookStateEnum.ZNISZCZONA);
        paymentService.destroyedBookPayment(bookState);
    }

    public Book latestLoanedBook(User user) { // PRZYKLAD
        List<Book> booksLoandedByUser = bookRepository.findLoanedBooksByUser(user);
        if (CollectionUtils.isEmpty(booksLoandedByUser)) {
            throw new ExceptionEmptyList("Aktualnie użytkownik nie ma wypożyczonych książek");
        }
        return booksLoandedByUser.stream().findFirst().get();
    }

    public Integer numberOfLoanedBooks(User user) {
        List<Book> booksLoandedByUser = bookRepository.findLoanedBooksByUser(user);
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
        if (Objects.isNull(sumPagesForUser)) {
            sumPagesForUser = 0;
            //ewentualnie można rzucić jakiegoś loga lub NoObjectException, ale czy jest sens?
        }
        return sumPagesForUser;
    }
}
