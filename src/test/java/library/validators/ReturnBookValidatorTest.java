package library.validators;

import library.TestUtils;
import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.repositories.UserRepository;
import library.users.User;
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


    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
        jdbcTemplate.update("delete from book_states where status=1020304050");
    }

    @Test
    public void wasBookReturnedInTime() {

        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        userRepository.save(user2);

        Action action = TestUtils.createAction(book, user);
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.WYPOZYCZONA);
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setUser(user);
        bookState.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState);

        BookState bookState1 = TestUtils.createBookState(book, action, BookStateEnum.WYPOZYCZONA);
        bookState1.setBook(book);
        bookState1.setAction(action);
        bookState1.setUser(user);
        bookState1.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState1);

        BookState bookState2 = TestUtils.createBookState(book, action, BookStateEnum.WYPOZYCZONA);
        bookState2.setBook(book);
        bookState2.setAction(action);
        bookState2.setUser(user);
        bookState2.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState2);

        BookState bookState3 = TestUtils.createBookState(book, action, BookStateEnum.WYPOZYCZONA);
        bookState3.setDateOfReturn(LocalDate.now().plusDays(15));
        bookState3.setBook(book);
        bookState3.setAction(action);
        bookState3.setUser(user2);
        bookState3.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState3);

        assertTrue(returnBookVaildator.validator(user));
        assertFalse(returnBookVaildator.validator(user2));

    }
}
