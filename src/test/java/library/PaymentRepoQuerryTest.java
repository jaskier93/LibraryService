package library;

import library.enums.BookStateEnum;
import library.models.*;
import library.repositories.*;
import library.users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PaymentRepoQuerryTest {

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


    @Test
    public void querryTest() {
        Book book = TestUtils.createBook();
     //   book.setAuthor(TestUtils.createAuthor());
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

   /*     Action action = TestUtils.createAction(book, user);
        actionRepository.save(action);

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.NOWA);
        bookStateRepository.save(bookState);*/

        Payment payment = TestUtils.createPayment(book, user);
        payment.setBook(book);
        payment.setUser(user);
     //   payment.setAction(action);
     //   payment.setAmount(15);
    //    payment.setBookState(bookState);
        paymentRepository.save(payment);

        Payment payment1 = paymentRepository.getOne(payment.getId());
        paymentRepository.save(payment1);

        assertEquals(payment.isActive(), payment1.isActive());

        assertFalse(paymentRepository.findByUser(user).isEmpty());
        assertFalse(paymentRepository.findPaymentsAboveAmount(8).isEmpty());
        assertTrue(paymentRepository.findPaymentsAboveAmount(18).isEmpty());

        paymentRepository.delete(payment);
        userRepository.delete(user);
        bookRepository.delete(book);
    }

}
