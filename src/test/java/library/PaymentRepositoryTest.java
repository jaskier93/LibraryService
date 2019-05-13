package library;

import library.enums.AgeCategory;
import library.enums.BookStateEnum;
import library.enums.Category;
import library.models.*;
import library.repositories.*;
import library.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PaymentRepositoryTest {

    @Autowired
    private final PaymentRepository paymentRepository = null;

    @Autowired
    private final BookStateRepository bookStateRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @After
    public void after() {
        //     jdbcTemplate.update("delete from actions");
        //   jdbcTemplate.update("delete from books");
        // jdbcTemplate.update("delete from author");
        jdbcTemplate.update("delete from user");
        jdbcTemplate.update("delete from payments");
        jdbcTemplate.update("delete from book_states");
    }

    @Test
    public void paymentTest() {

/*        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = new Action();
      //  action.setActionDescription("x");
        //action.setUser(user);
        actionRepository.save(action);

        BookState bookState = TestUtils.createBookState(TestUtils.createBook(), action, BookStateEnum.NOWA);
        bookStateRepository.save(bookState);*/

        Payment payment = new Payment();

        payment = TestUtils.createPayment(TestUtils.createBook(), TestUtils.createUser());
/*        payment.setAmount(10);
        payment.setActive(true);
        payment.setBook(book);
        payment.setUser(user);
        payment.setStatus(0);*/
        //  payment.setBookState(bookState);

  /*      Payment payment2 = new Payment();
        payment2.setAmount(20);
        payment2.setActive(false);
        payment2.setBook(book);
        payment2.setUser(user);
        payment2.setStatus(5);
        payment2.setBookState(bookState);
        paymentRepository.save(payment2);*/

        assertTrue(payment.isActive());

        Payment payment3 = paymentRepository.getOne(payment.getId());

   /*     assertEquals(payment.getAction(), payment2.getAction());
        assertNotEquals(payment.isActive(), payment2.isActive());
        assertNotEquals(payment.getAmount(), payment2.getAmount());
        assertNotEquals(payment.getStatus(), payment2.getStatus());
        paymentRepository.delete(payment2);*/

        assertEquals(payment3.isActive(), payment.isActive());
        assertEquals(payment3.getStatus(), payment.getStatus());
        assertEquals(payment3.getUser(), payment.getUser());

        paymentRepository.delete(payment);

        //   bookStateRepository.delete(bookState);
        //    actionRepository.delete(action);
        //    userRepository.delete(user);
    }


}
