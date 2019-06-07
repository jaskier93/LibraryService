package library.validators.mainValidators;

import library.TestUtils;
import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.models.Payment;
import library.repositories.*;
import library.users.User;
import library.validators.mainValidators.PaymentSumValidator;
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
public class PaymentSumValidatorTest {

    @Autowired
    private final PaymentSumValidator paymentSumValidator = null;

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

/*    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
        jdbcTemplate.update("delete from book_states where status=1020304050");
        jdbcTemplate.update("delete from payments where status=1020304050");
    }*/

    @Test
    //test passed! TODO: poprawić w paymentRepo metodę sumowanią, tak sumowała tylko aktywne (niezapłacone) płatnośći
    public void isUserPaymentsSumAbove100() {

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

        Action action2 = TestUtils.createAction(book, user);
        action2.setBook(book);
        action2.setUser(user2);
        actionRepository.save(action);

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.ZWROCONA);
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setUser(user);
        bookState.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState);

        Payment payment = TestUtils.createPayment(action);
        payment.setAmount(70);
        payment.setBook(action.getBook());
        payment.setAction(action);
        payment.setBookState(bookState);
        paymentRepository.save(payment);

        Payment payment2 = TestUtils.createPayment(action);
        payment2.setAmount(50);
        payment2.setBook(book);
        payment2.setAction(action);
        payment2.setBookState(bookState);
        paymentRepository.save(payment2);

        Payment payment3 = TestUtils.createPayment(action2);
        payment3.setAmount(55);
        payment3.setBook(book);
        payment3.setAction(action2);
        payment3.setBookState(bookState);
        paymentRepository.save(payment3);

        assertTrue(paymentSumValidator.validator(user));
        assertFalse(paymentSumValidator.validator(user2));
    }
}
