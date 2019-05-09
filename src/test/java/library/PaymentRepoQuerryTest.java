package library;

import library.models.Book;
import library.models.Payment;
import library.repositories.BookRepository;
import library.repositories.PaymentRepository;
import library.repositories.UserRepository;
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

    @Test
    private void querryTest() {
        Book book = TestUtils.createBook();
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Payment payment = TestUtils.createPayment(book, user);
        paymentRepository.save(payment);

        Payment payment1 = paymentRepository.getOne(payment.getId());
        paymentRepository.save(payment1);

        assertEquals(payment.isActive(), payment1.isActive());

        assertEquals(true, !paymentRepository.findByUser(user).isEmpty());
        assertEquals(true, !paymentRepository.findByAmount(8).isEmpty());
    }

}
