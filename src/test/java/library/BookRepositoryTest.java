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
    private final ActionRepository actionRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from actions");
        jdbcTemplate.update("delete from books");
        jdbcTemplate.update("delete from author");
        jdbcTemplate.update("delete from user");
        jdbcTemplate.update("delete from book_states");
    }

    //test passed
    @Test
    public void bookTest() {
        Book book = TestUtils.createBook();
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = TestUtils.createAction(book, user);
        actionRepository.save(action);

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.NOWA);
        bookState.setUser(user);
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setBookStateEnum(BookStateEnum.NOWA);
        bookStateRepository.save(bookState);

        assertNotNull(bookState);
        assertNotNull(book.getId());
        assertEquals(bookState.getBook(), book);

        Book bookFromBase = bookRepository.getOne(book.getId());

        assertFalse(book.getTitle().isEmpty());
        assertEquals(book.getId(), bookFromBase.getId());
        assertEquals(bookFromBase.getReleaseDate(), book.getReleaseDate());
    }
}