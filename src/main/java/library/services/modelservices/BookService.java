package library.services.modelservices;

import library.enums.ActionDescription;
import library.enums.AgeCategory;
import library.enums.BookStateEnum;
import library.enums.Category;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.models.Payment;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.repositories.PaymentRepository;
import library.services.exceptions.ExceptionFactory;
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

            /**        action.setUser(); tutaj powinno dodawać się login admina
             *         login można wyciągnąć w kontrolerze z akutalnej sesji gdy user jest zalogowany,
             *         dodatkowo walidacja czy ma status admina
             *         taką walidację można zrobić dwojako: sprawdzić czy isAdmin(true)
             *         lub odpowiednia adnotacja (trzeba by dodać całe security)
             */
            Action action = actionService.updateBook(book, user);

            //TODO: na 99% bookState tutaj nie będzie potrzebny, do przemyślenia
    /*        BookState newBookState = new BookState();
            BookState bookStateFromBase = bookStateRepository.findBookStateByBook(book.getId());
            newBookState.setBook(bookFromBase);
            newBookState.setAction(action);
            newBookState.setUpdated(LocalDateTime.now());
            newBookState.setUser(bookStateFromBase.getUser());
            newBookState.setBookStateEnum(bookStateFromBase.getBookStateEnum());
            newBookState.setStatus(bookStateFromBase.getStatus());
            newBookState.setCreated(LocalDateTime.now());
            newBookState.setUpdated(LocalDateTime.now());
            newBookState.setDateOfLoan(bookStateFromBase.getDateOfLoan());
            newBookState.setDateOfReturn(bookStateFromBase.getDateOfReturn());
            bookStateRepository.save(newBookState);*/
        }
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


    /**
     * TODO: w tych dwóch metodach może trzeba będzie zmienić parametry na Stringi
     */
//    public List<Book> booksByCategory(Category category) {
//        List<Book> booksByCat = bookRepository.findBookByCategory(category);
//        if(CollectionUtils.isEmpty(booksByCat)){
////            throw ExceptionFactory.trowException()
//        }
//        return ;
//    }
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
        /*TODO
         * Do dodania:
         * repozytoria: akcji, płatnośći
         * pola w bookstate-user (prawdopodobnie
         * testy-sprawdzenie, czy dodajac /edytujac ksiazke, wypozyczenie, etc czy w odpowiednich repozytoriach tworza sie odpowiednia odniesienia do danej ksiazki, usera itd
         * */
    }
}
