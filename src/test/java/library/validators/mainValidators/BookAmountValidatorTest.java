package library.validators.mainValidators;

import library.TestUtils;
import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.models.BookState;
import library.repositories.*;
import library.users.User;
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
    private final BookAmountValidator bookAmountValidator = null;

    @Autowired
    private final BookStateRepository bookStateRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
        jdbcTemplate.update("delete from book_states  where status=1020304050");
    }

    @Test //test passed! obiekty prawidłowo usuwane z bazy
    public void isUserAbleToLoan4thBook() {

        Author author = TestUtils.createAuthor();
        authorRepository.save(author);


        Book book = TestUtils.createBook(author);
        bookRepository.save(book);
        Book book1 = TestUtils.createBook(author);
        bookRepository.save(book1);
        Book book2 =  TestUtils.createBook(author);
        bookRepository.save(book2);
        Book book3 =  TestUtils.createBook(author);
        bookRepository.save(book3);
        Book book4 =  TestUtils.createBook(author);
        bookRepository.save(book4);
        Book book5 =  TestUtils.createBook(author);
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

        BookState bookState = TestUtils.createBookState(action, BookStateEnum.WYPOZYCZONA);

        bookState.setBook(book);
        bookState.setAction(action);
        bookStateRepository.save(bookState);

        BookState bookState1 = TestUtils.createBookState(action1, BookStateEnum.WYPOZYCZONA);
        bookState1.setBook(book1);
        bookState1.setAction(action1);
        bookStateRepository.save(bookState1);

        BookState bookState2 = TestUtils.createBookState(action2, BookStateEnum.WYPOZYCZONA);
        bookState2.setBook(book2);
        bookState2.setAction(action2);
        bookStateRepository.save(bookState2);

        BookState bookState3 = TestUtils.createBookState(action3, BookStateEnum.WYPOZYCZONA);
        bookState3.setBook(book3);
        bookState3.setAction(action3);
        bookStateRepository.save(bookState3);

        BookState bookState4 = TestUtils.createBookState(action4, BookStateEnum.WYPOZYCZONA);
        bookState4.setBook(book4);
        bookState4.setAction(action4);
        bookStateRepository.save(bookState4);

        BookState bookState5 = TestUtils.createBookState(action5, BookStateEnum.WYPOZYCZONA);
        bookState5.setBook(book5);
        bookState5.setAction(action5);
        bookStateRepository.save(bookState5);

        //test ma zwrócić false, użytkownik ma przynajmniej 4 książki wypożyczone
        assertFalse(bookAmountValidator.validator(user));

        //test ma zwrócić true, użytkownik ma maksymalnie 3 książki wypożyczone
        assertTrue(bookAmountValidator.validator(user2));
    }
}
