package library.services;

import library.enums.BookStateEnum;
import library.models.Book;
import library.models.BookState;
import library.models.Payment;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookService {

    //kara 20zł za zniszczenie książki
    static final Integer PENALTY_AMOUNT = 20;


    private BookRepository bookRepository;
    private BookStateRepository bookStateRepository;

    @Autowired
    public BookService(BookRepository bookRepository, BookStateRepository bookStateRepository) {
        this.bookRepository = bookRepository;
        this.bookStateRepository = bookStateRepository;
    }

    private Book addBook(Book book) {
        book.setTitle(book.getTitle());
        book.setAddingDate(LocalDate.now());
        book.setAgeCategory(book.getAgeCategory());
        book.setAuthor(book.getAuthor());
        book.setCategory(book.getCategory());
        book.setReleaseDate(book.getReleaseDate());
        book.setStatus(book.getStatus()); //czy status będzie ustawiany przez użytkownika (lub zależnie od wprowadzonych przez niego danych) czy przez nas?

        BookState bookState = new BookState();
        bookState.setBookStateEnum(BookStateEnum.NOWA);
        bookState.setBook(book);
        bookState.setDateOfCreating(book.getAddingDate());
        bookState.setAction(null);
        bookState.setDateOfReturn(null);
        bookState.setDateOfUpdating(null);
        bookState.setStatus(book.getStatus()); //czy wartość Integer status z klas Book i BookState będzie odpowiadać za to samo?
        bookStateRepository.save(bookState);

        return bookRepository.save(book);
    }

    private Book updateBook(Book book) {
        //:TODO
        return bookRepository.save(book);
    }


    //metoda usuwa książkę ze zbioru dostępnych do wypożyczenia książek nadając jej status ZNISZCZONA
    //ustala też umowną karę dla użytkownika za zniszczenie książki
    public Book deleteBook(Book book, User user) {

        BookState bookState = new BookState();
        bookState.setBookStateEnum(BookStateEnum.ZNISZCZONA);
        bookState.setDateOfReturn(LocalDate.now());
        bookState.setDateOfUpdating(LocalDate.now());

        Payment payment = new Payment();
        payment.setAmount(PENALTY_AMOUNT);
        payment.setUser(user);
        payment.setActive(true);
        payment.setBook(book);
        return bookRepository.save(book);
    }

}
