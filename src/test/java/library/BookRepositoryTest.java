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

        Book book = TestUtils.createBook();
        //TODO: metody save nie zapisują obiektów do repozytoriów-do naprawy!
        bookRepository.save(book);

        Book book2 = TestUtils.createBook();
        bookRepository.save(book2);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = TestUtils.createAction(book, user);
        actionRepository.save(action);

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.NOWA);
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
        userRepository.delete(user);

    }
}