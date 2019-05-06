package library;

import library.enums.AgeCategory;
import library.enums.BookStateEnum;
import library.enums.Category;
import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.models.BookState;
import library.repositories.ActionRepository;
import library.repositories.AuthorRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookRepositoryTest {

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final BookStateRepository bookStateRepository = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    private Author author = new Author("Andrzej", "", "Sapkowski", LocalDate.of(2015, 12, 31),
            LocalDate.of(2015, 12, 31), LocalDate.of(2015, 12, 31), 5);


    @Before
    public void setUp() {
        authorRepository.save(author);
    }

    @After
    public void after() {
        authorRepository.delete(author);
    }

    @Test
    public void bookTest() {

        Book book = new Book("Wiedźmin", LocalDate.of(2015, 11, 22), LocalDate.of(2015, 12, 21),
                Category.ADVENTURE, AgeCategory.DOROŚLI, author, 5);
        bookRepository.save(book);

        Book book2 = new Book("Wiedźmin", LocalDate.of(2015, 11, 22), LocalDate.of(2015, 12, 21),
                Category.ADVENTURE, AgeCategory.DOROŚLI, author, 5);
        bookRepository.save(book2);

        Action action = new Action();
        action.setActionDescription("x");
        action.setBook(book);
        action.setUser(null);
        actionRepository.save(action);

        BookState bookState = new BookState();
        bookState.setBook(book);
        bookState.setBookStateEnum(BookStateEnum.NOWA);
        bookState.setDateOfReturn(LocalDate.now());
        bookState.setDateOfUpdating(LocalDate.now());
        bookState.setDateOfCreating(LocalDate.now());
        bookState.setDateOfLoan(LocalDate.now());
        bookState.setAction(action);
        bookState.setStatus(0);
        bookStateRepository.save(bookState);

        assertNotNull(bookState);
        assertEquals(bookState.getBook(), book);

        assertNotNull(book.getId());
        assertNotEquals(book.getId(), book2.getId());

        Book bookFromBase = bookRepository.getOne(book2.getId());

        assertEquals(bookFromBase.getReleaseDate(), book2.getReleaseDate());
        assertEquals(bookFromBase.getReleaseDate(), book2.getReleaseDate());
        assertEquals(bookFromBase.getId(), book2.getId());

        //dodać usuwanie poprzednich rzeczy
        bookStateRepository.delete(bookState);
        actionRepository.delete(action);
        bookRepository.delete(book);
        bookRepository.delete(book2);


    }
}