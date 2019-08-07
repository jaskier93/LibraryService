package library.validators.mainValidators;

import library.TestUtils;
import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.models.BookState;
import library.repositories.*;
import library.models.User;
import org.junit.After;
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
public class ReturnBookValidatorTest {

    @Autowired
    private final ReturnBookVaildator returnBookVaildator = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final BookStateRepository bookStateRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from book_states where status=1020304050");
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    @Test
    public void wasBookReturnedInTime() {

        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Book book = TestUtils.createBook(author);
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        userRepository.save(user2);

        Action action = TestUtils.createAction(book, user);
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        Action action2 = TestUtils.createAction(book, user2);
        action2.setBook(book);
        action2.setUser(user2);
        actionRepository.save(action2);

        BookState bookState = TestUtils.createBookState( action, BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState);

        BookState bookState1 = TestUtils.createBookState(action, BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState1);

        BookState bookState2 = TestUtils.createBookState(action, BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState2);

        BookState bookState3 = TestUtils.createBookState(action2, BookStateEnum.WYPOZYCZONA);
        bookState3.setDateTo(LocalDate.now().plusDays(15));
        bookStateRepository.save(bookState3);

        assertEquals(true, returnBookVaildator.validator(user));
        assertFalse(returnBookVaildator.validator(user2));
    }
}
