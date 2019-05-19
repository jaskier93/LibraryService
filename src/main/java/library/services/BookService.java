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
        //   bookState.setDateOfCreating(book.getAddingDate());
        bookState.setAction(action);
        bookState.setDateOfReturn(null);
        bookState.setDateOfUpdating(LocalDate.now());
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

    private Book updateBook(Book book) {
        Book book2 = bookRepository.getOne(book.getId());
        book2.setTitle(book.getTitle());
        //   book2.setAddingDate(LocalDate.now()); //ta zmienna nie powinna być zmieniana
        book2.setAgeCategory(book.getAgeCategory());
        book2.setAuthor(book.getAuthor());
        book2.setCategory(book.getCategory());
        book2.setReleaseDate(book.getReleaseDate());
        book2.setStatus(0);

        Action action = new Action();
        action.setBook(book2);
        /**
         *         action.setUser(); tutaj powinno dodawać się login admina
         *         login można wyciągnąć w kontrolerze z akutalnej sesji gdy user jest zalogowany,
         *         dodatkowo walidacja czy ma status admina
         *         taką walidację można zrobić dwojako: sprawdzić czy isAdmin(true)
         *         lub odpowiednia adnotacja (trzeba by dodać całe security)
         */
        action.setActionDescription("Zaktualizowanie informacji o książce");
        actionRepository.save(action);

        //zrobić test
        BookState bookState2 = new BookState();
        BookState bookState = bookStateRepository.findBookStateByBook(book.getId());
        bookState2.setBook(book2);
        bookState2.setAction(action);
        bookState2.setDateOfUpdating(LocalDate.now());
        bookState2.setUser(bookState.getUser());
        bookState2.setBookStateEnum(bookState.getBookStateEnum());
        bookState2.setStatus(bookState.getStatus());
        bookState2.setDateOfCreating(LocalDate.now());
        bookState2.setDateOfUpdating(LocalDate.now());
        bookState2.setDateOfLoan(bookState.getDateOfLoan());
        bookState2.setDateOfReturn(bookState.getDateOfReturn());
        bookStateRepository.save(bookState2);

        return bookRepository.save(book2);
    }

    public List<Book> sortedBooksByReleaseDate() {
        return bookRepository.sortedBooksByReleaseData();
    }

    public List<Book> sortedBooksByAddingDate() {
        return bookRepository.sortedBooksByAddingData();
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
        Action action = new Action();
        action.setUser(user);
        action.setBook(bookRepository.getOne(bookId));
        action.setActionDescription("Książka zniszczona");
        actionRepository.save(action);

        BookState bookState = new BookState();
        bookState.setBookStateEnum(BookStateEnum.ZNISZCZONA);
        bookState.setDateOfReturn(LocalDate.now());
        bookState.setDateOfUpdating(LocalDate.now());
        bookStateRepository.save(bookState);

        Payment payment = new Payment();
        payment.setAmount(PENALTY_AMOUNT);
        payment.setUser(user);
        payment.setActive(true);
        payment.setBook(bookRepository.getOne(bookId));
        paymentRepository.save(payment);

        /*TODO
         * Do dodania:
         * repozytoria: akcji, płatnośći
         * pola w bookstate-user (prawdopodobnie
         * testy-sprawdzenie, czy dodajac /edytujac ksiazke, wypozyczenie, etc czy w odpowiednich repozytoriach tworza sie odpowiednia odniesienia do danej ksiazki, usera itd
         * */
    }

}
