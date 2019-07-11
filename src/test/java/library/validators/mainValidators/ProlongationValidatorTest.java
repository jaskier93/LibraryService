package library.validators.mainValidators;

import library.TestUtils;
import library.enums.BookStateEnum;
import library.models.*;
import library.repositories.*;
import library.models.User;
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
public class ProlongationValidatorTest {

    @Autowired
    private final ProlongationValidator prolongationValidator = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final PaymentRepository paymentRepository = null;

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
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
        jdbcTemplate.update("delete from book_states where status=1020304050");
    }

    @Test //test passed! prawidłowo usuwa obiekty
    public void isUserAbleToExtendLoan() {

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

        BookState bookState = TestUtils.createBookState(action, BookStateEnum.ZWROCONA);
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState);

        Payment payment = TestUtils.createPayment(action2);
        payment.setBook(book);
        payment.setAmount(50);
        payment.setAction(action2);
        payment.setBookState(bookState);
        paymentRepository.save(payment);

        /*
         *test określa, czy user może przedłużyć wypożyczenie książki
         *tutaj może, bo ma tylko jedną wypożyczoną książkę oraz nie ma żadnej naliczonej płatności
         */
        assertTrue(prolongationValidator.validator(user));

        /**
         *test określa, czy user może przedłużyć wypożyczenie książki
         * tutaj nie może, ponieważ ma naliczoną płatność
         */
        assertFalse(prolongationValidator.validator(user2));
    }
}




