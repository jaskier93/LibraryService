package library;

import library.enums.BookStateEnum;
import library.models.Author;
import library.models.Book;
import library.models.BookState;
import library.models.Payment;
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
    private AuthorRepository authorRepository = null;

    @Autowired
    private BookStateRepository bookStateRepository=null;


    //test nie przechodzi póki co, problem z zapisem płatności do repo
    @Test
    public void querryTest() {
        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Book book = TestUtils.createBook();
        book.setAuthor(author);
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);


        Payment payment = TestUtils.createPayment(book, user);
        payment.setBook(book);
        payment.setUser(user);
        payment.setAmount(15);
        payment.setBookState(TestUtils.createBookState(book, TestUtils.createAction(book, user), BookStateEnum.NOWA));
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
        authorRepository.delete(author);
    }

}
