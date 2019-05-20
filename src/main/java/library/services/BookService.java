package library.services;

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
import library.users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class BookService {

    //kara 20zł za zniszczenie książki
    public static final Integer PENALTY_AMOUNT = 20;

    private BookRepository bookRepository;
    private BookStateRepository bookStateRepository;
    private ActionRepository actionRepository;
    private PaymentRepository paymentRepository;

    @Autowired
    public BookService(BookRepository bookRepository, BookStateRepository bookStateRepository, ActionRepository actionRepository, PaymentRepository paymentRepository) {
        this.bookRepository = bookRepository;
        this.bookStateRepository = bookStateRepository;
        this.actionRepository = actionRepository;
        this.paymentRepository = paymentRepository;
    }

    private Book addBook(Book book) {
        Action action = new Action();
        action.setBook(book);
        //action.setUser(); tutaj powinno dodawać się login admina
        action.setActionDescription("Dodanie nowej książki");
        actionRepository.save(action);

        BookState bookState = new BookState();
        bookState.setBookStateEnum(BookStateEnum.NOWA);
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setDateOfReturn(null); //tutaj powinno być settowanie na nulla, ponieważ w klasie BooState domyślnie ustawia na obecną datę +30dni
        bookState.setStatus(0); //wartość tymczasowa, później się ustali TODO
        bookStateRepository.save(bookState);

        return bookRepository.save(book);
    }

    /**
     * metoda zwraca BSEnuma-info o statusie ksiązki, czy jest wypożyczona/zwrócona/nowa/zniszczona etc
     */
    private BookStateEnum getBookStateEnum(Integer bookId) {
        return bookStateRepository.findBookStateByBook(bookId).getBookStateEnum();
    }

    private Book updateBook(Book book, Integer bookId) {
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

            Action action = new Action();
            action.setBook(bookFromBase);
            /**
             *         action.setUser(); tutaj powinno dodawać się login admina
             *         login można wyciągnąć w kontrolerze z akutalnej sesji gdy user jest zalogowany,
             *         dodatkowo walidacja czy ma status admina
             *         taką walidację można zrobić dwojako: sprawdzić czy isAdmin(true)
             *         lub odpowiednia adnotacja (trzeba by dodać całe security)
             */
            action.setActionDescription("Zaktualizowanie informacji o książce"); //TODO: actionENUM
            actionRepository.save(action);

            BookState newBookState = new BookState();
            BookState bookStateFromBase = bookStateRepository.findBookStateByBook(book.getId());
            newBookState.setBook(bookFromBase);
            newBookState.setAction(action);
            newBookState.setDateOfUpdating(LocalDate.now());
            newBookState.setUser(bookStateFromBase.getUser());
            newBookState.setBookStateEnum(bookStateFromBase.getBookStateEnum());
            newBookState.setStatus(bookStateFromBase.getStatus());
            newBookState.setDateOfCreating(LocalDate.now());
            newBookState.setDateOfUpdating(LocalDate.now());
            newBookState.setDateOfLoan(bookStateFromBase.getDateOfLoan());
            newBookState.setDateOfReturn(bookStateFromBase.getDateOfReturn());
            bookStateRepository.save(newBookState);
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
        return bookRepository.booksReleasedInPeriod(LocalDate.now().minusYears(1));
    }

    //książki dodane w ciągu miesiąca-nowości w bibliotece
    public List<Book> booksAddedInPeriod() {
        return bookRepository.booksAddedInPeriod(LocalDate.now().minusMonths(1));
    }


    /**
     * TODO: w tych dwóch metodach może trzeba będzie zmienić parametry na Stringi
     */
    public List<Book> booksByCategory(Category category) {
        return bookRepository.findBookByCategory(category);
    }

    public List<Book> booksByAgeCategory(AgeCategory ageCategory) {
        return bookRepository.findBookByAgeCategory(ageCategory);
    }

    /**
     * metoda usuwa książkę ze zbioru dostępnych do wypożyczenia książek nadając jej status ZNISZCZONA
     * ustala też umowną karę dla użytkownika za zniszczenie książki
     */
    public void deleteBook(Integer bookId, User user) {
        Book bookFromBase = bookRepository.getOne(bookId);
        Action action = new Action();
        action.setUser(user);
        action.setBook(bookFromBase);
        action.setActionDescription("Książka zniszczona");
        actionRepository.save(action);

        BookState bookState = new BookState();
        bookState.setBookStateEnum(BookStateEnum.ZNISZCZONA);
        bookState.setAction(action);
        bookState.setStatus(0);
        bookState.setUser(user);
        bookState.setBook(bookFromBase);
        bookState.setDateOfReturn(null);
        bookStateRepository.save(bookState);

        Payment payment = new Payment();
        payment.setAmount(PENALTY_AMOUNT);
        payment.setUser(user);
        payment.setActive(true);
        payment.setBook(bookFromBase);
        paymentRepository.save(payment);

        /*TODO
         * Do dodania:
         * repozytoria: akcji, płatnośći
         * pola w bookstate-user (prawdopodobnie
         * testy-sprawdzenie, czy dodajac /edytujac ksiazke, wypozyczenie, etc czy w odpowiednich repozytoriach tworza sie odpowiednia odniesienia do danej ksiazki, usera itd
         * */
    }

}
