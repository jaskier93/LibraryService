package library.validatorsTests;

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
import library.validators.BookAmountValidator;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookAmountValidatorTest {

    @Autowired
    private BookAmountValidator bookAmountValidator;

    @Autowired
    private BookStateRepository bookStateRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
        jdbcTemplate.update("delete from book_states where status=1020304050");
    }

    @Test
    public void validatorTest() {

        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);
        Book book1 = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book1);
        Book book2 = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book2);
        Book book3 = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book3);
        Book book4 = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book4);
        Book book5 = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book5);

        User user = TestUtils.createUser();
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        userRepository.save(user2);

        Action action = TestUtils.createAction(book, user);
        action.setUser(user);
        action.setBook(book);
        actionRepository.save(action);

        Action action1 = TestUtils.createAction(book1, user);
        action1.setUser(user);
        action1.setBook(book1);
        actionRepository.save(action1);

        Action action2 = TestUtils.createAction(book2, user);
        action2.setUser(user);
        action2.setBook(book2);
        actionRepository.save(action2);

        Action action3 = TestUtils.createAction(book3, user);
        action3.setUser(user);
        action3.setBook(book3);
        actionRepository.save(action3);

        Action action4 = TestUtils.createAction(book4, user);
        action4.setUser(user);
        action4.setBook(book4);
        actionRepository.save(action4);

        Action action5 = TestUtils.createAction(book5, user);
        action5.setUser(user);
        action5.setBook(book5);
        actionRepository.save(action5);

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.WYPOZYCZONA);
        bookState.setUser(user);
        bookState.setBook(book);
        bookState.setAction(action);
        bookStateRepository.save(bookState);

        BookState bookState1 = TestUtils.createBookState(book1, action1, BookStateEnum.WYPOZYCZONA);
        bookState1.setUser(user);
        bookState1.setBook(book1);
        bookState1.setAction(action1);
        bookStateRepository.save(bookState1);

        BookState bookState2 = TestUtils.createBookState(book2, action2, BookStateEnum.WYPOZYCZONA);
        bookState2.setUser(user);
        bookState2.setBook(book2);
        bookState2.setAction(action2);
        bookStateRepository.save(bookState2);

        BookState bookState3 = TestUtils.createBookState(book3, action3, BookStateEnum.WYPOZYCZONA);
        bookState3.setUser(user);
        bookState3.setBook(book3);
        bookState3.setAction(action3);
        bookStateRepository.save(bookState3);

        BookState bookState4 = TestUtils.createBookState(book4, action4, BookStateEnum.WYPOZYCZONA);
        bookState4.setUser(user);
        bookState4.setBook(book4);
        bookState4.setAction(action4);
        bookStateRepository.save(bookState4);

        BookState bookState5 = TestUtils.createBookState(book5, action5, BookStateEnum.WYPOZYCZONA);
        bookState5.setUser(user2);
        bookState5.setBook(book5);
        bookState5.setAction(action5);
        bookStateRepository.save(bookState5);

        assertTrue(bookAmountValidator.validator(user));
        assertFalse(bookAmountValidator.validator(user2));
    }
}
