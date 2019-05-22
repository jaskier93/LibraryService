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
public class RentFifthBookValidatorTest {

    @Autowired
    private final RentFifthBookValidator rentFifthBookValidator = null;

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
    public void isUserAbleToLoan5thBook() {

        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);

        User user = TestUtils.createUser();
        user.setDateOfRegistration(LocalDate.now().minusYears(3));
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        user2.setDateOfRegistration(LocalDate.now().minusMonths(3));
        userRepository.save(user2);

        Action action = TestUtils.createAction(book, user);
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.NOWA);
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setUser(user);
        bookState.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState);

        BookState bookState1 = TestUtils.createBookState(book, action, BookStateEnum.NOWA);
        bookState1.setBook(book);
        bookState1.setAction(action);
        bookState1.setUser(user);
        bookState1.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState1);

        BookState bookState2 = TestUtils.createBookState(book, action, BookStateEnum.NOWA);
        bookState2.setBook(book);
        bookState2.setAction(action);
        bookState2.setUser(user);
        bookState2.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState2);

        BookState bookState3 = TestUtils.createBookState(book, action, BookStateEnum.NOWA);
        bookState3.setBook(book);
        bookState3.setAction(action);
        bookState3.setUser(user);
        bookState3.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState3);

        //metoda zwraca false, bo user nie ma żadnego wypożyczenia na koncie, na dodatek jest zarejestrowany dopiero 3 miesiące
        assertFalse(rentFifthBookValidator.validator(user2));

        //metoda zwraca true, bo użytkownik ma dokładnie 4 wypożyczenia oraz jest zarejestrowany przynajmniej rok
        assertTrue(rentFifthBookValidator.validator(user));
    }
}