package library.validators;

import library.TestUtils;
import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.models.Payment;
import library.repositories.*;
import library.users.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PaymentAmountValidatorTest {

    @Autowired
    private final PaymentAmountValidator paymentAmountValidator = null;

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

    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
        jdbcTemplate.update("delete from book_states where status=1020304050");
        jdbcTemplate.update("delete from payments where status=1020304050");
    }

    @Test //test passed! prawidłowo usuwa obiekty
    public void hasUserAtLeastThan3Payments() {
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

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.NOWA);
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setUser(user);
        bookState.setBookStateEnum(BookStateEnum.NOWA);
        bookStateRepository.save(bookState);

        Payment payment = TestUtils.createPayment(book, user);
        payment.setBook(book);
        payment.setUser(user);
        payment.setAction(action);
        payment.setBookState(bookState);
        paymentRepository.save(payment);

        Payment payment2 = TestUtils.createPayment(book, user);
        payment2.setBook(book);
        payment2.setUser(user);
        payment2.setAction(action);
        payment2.setBookState(bookState);
        paymentRepository.save(payment2);

        Payment payment3 = TestUtils.createPayment(book, user);
        payment3.setBook(book);
        payment3.setUser(user);
        payment3.setAction(action);
        payment3.setBookState(bookState);
        paymentRepository.save(payment3);

        assertTrue(paymentAmountValidator.validator(user));
        assertFalse(paymentAmountValidator.validator(user2));
    }
}
