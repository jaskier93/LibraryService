package library.repositories;

import library.TestUtils;
import library.enums.BookStateEnum;
import library.models.*;
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
public class PaymentRepositoryTest {

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final PaymentRepository paymentRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private BookStateRepository bookStateRepository = null;

    @Autowired
    private ActionRepository actionRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
        jdbcTemplate.update("delete from book_states where status=1020304050");
        jdbcTemplate.update("delete from payments  where status=1020304050");
    }

    //test passed!
    @Test
    public void paymentRepositoryTest() {
        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = TestUtils.createAction(book, user);
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.ZWROCONA);
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setUser(user);
        bookState.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState);

        Payment payment = TestUtils.createPayment(book, user);
        payment.setAmount(50);
        payment.setBook(book);
        payment.setUser(user);
        payment.setAction(action);
        payment.setBookState(bookState);
        paymentRepository.save(payment);

        Payment payment2 = TestUtils.createPayment(book, user);
        payment2.setAmount(150);
        payment2.setBook(book);
        payment2.setUser(user);
        payment2.setAction(action);
        payment2.setBookState(bookState);
        paymentRepository.save(payment2);

        Payment payment1 = paymentRepository.getOne(payment.getId());
        paymentRepository.save(payment1);

        assertEquals(payment.isActive(), payment1.isActive());

        assertFalse(paymentRepository.findByUser(user).isEmpty());
        assertFalse(paymentRepository.findPaymentsAboveAmount(8).isEmpty());
        assertTrue(paymentRepository.findPaymentsAboveAmount(1555454558).isEmpty());
        assertEquals(Integer.valueOf(200), paymentRepository.sumPaymentsForOneUser(user.getId()));
    }
}
