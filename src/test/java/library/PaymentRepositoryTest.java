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
    private final UserRepository userRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @After
    public void after() {
        //     jdbcTemplate.update("delete from actions");
          jdbcTemplate.update("delete from books");
        // jdbcTemplate.update("delete from author");
        jdbcTemplate.update("delete from user");
        jdbcTemplate.update("delete from payments");
     //   jdbcTemplate.update("delete from book_states");
    }

    @Test
    public void paymentTest() {
        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Book book = TestUtils.createBook(author);
        bookRepository.save(book);


        User user = TestUtils.createUser();
        userRepository.save(user);

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

        assertTrue(payment.isActive());

        Payment payment2 = paymentRepository.getOne(payment.getId());

        assertEquals(payment.getAction(), payment2.getAction());
        assertEquals(payment.getId(), payment2.getId());
        assertEquals(payment2.getStatus(), payment.getStatus());
        assertEquals(payment2.getUser(), payment.getUser());

        assertTrue(payment.isActive());
        assertNotNull(payment);
    }
}
