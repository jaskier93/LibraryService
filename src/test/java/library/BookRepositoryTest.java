package library;

import library.enums.AgeCategory;
import library.enums.BookStateEnum;
import library.enums.Category;
import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.models.BookState;
import library.repositories.*;
import library.users.User;
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
    private final UserRepository userRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    private Author author = TestUtils.createAuthor();

    @Before
    public void setUp() {
        authorRepository.save(author);
    }

    @After
    public void after() {
        authorRepository.delete(author);
    }

    //TODO:test do poprawy
    @Test
    public void bookTest() {

        Book book = TestUtils.createBook();
        book.setAuthor(author);
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = TestUtils.createAction(book, user);
        action.setUser(user);
        action.setBook(book);
        actionRepository.save(action);

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.NOWA);
        bookState.setUser(user);
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setBookStateEnum(BookStateEnum.NOWA);
        bookStateRepository.save(bookState);

        assertNotNull(bookState);
        assertEquals(bookState.getBook(), book);


        Book bookFromBase = bookRepository.getOne(book.getId());
        assertNotNull(book.getId());
        assertEquals(book.getId(), bookFromBase.getId());
        assertEquals(bookFromBase.getReleaseDate(), book.getReleaseDate());
        assertEquals(bookFromBase.getReleaseDate(), book.getReleaseDate());
        assertEquals(bookFromBase.getId(), book.getId());


        bookStateRepository.delete(bookState);
        actionRepository.delete(action);
        bookRepository.delete(book);
        userRepository.delete(user);

    }
}