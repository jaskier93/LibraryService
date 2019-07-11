package library.validators;

import library.TestUtils;
import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.models.BookState;
import library.repositories.*;
import library.models.User;
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
public class IsBookLoanableTest {

    @Autowired
    private final IsBookLoanable isBookLoanable = null;

    @Autowired
    private final BookStateRepository bookStateRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

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

    @Test //test passed! prawdiłowo usuwane obiekty z bazy
    public void isBookLoanable() {

        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Book book = TestUtils.createBook(author);
        bookRepository.save(book);

        Book book1 = TestUtils.createBook(author);
        bookRepository.save(book1);

        Book book2 = TestUtils.createBook(author);
        bookRepository.save(book2);

        User user = TestUtils.createUser();
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        userRepository.save(user2);

        Action action = TestUtils.createAction(book, user);
        action.setUser(user);
        action.setBook(book);
        actionRepository.save(action);

        Action action1 = TestUtils.createAction(book1, user);
        action1.setUser(user);
        action1.setBook(book1);
        actionRepository.save(action1);

        Action action2 = TestUtils.createAction(book2, user);
        action2.setUser(user);
        action2.setBook(book2);
        actionRepository.save(action2);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        BookState bookState = TestUtils.createBookState(action, BookStateEnum.ZWROCONA);
        bookState.setDateOfReturn(LocalDate.now().plusDays(1));
        bookState.setDateOfLoan(LocalDate.now().plusDays(1));
        bookState.setBook(book);
        bookState.setAction(action);
        bookStateRepository.save(bookState);

        BookState bookState2 = TestUtils.createBookState(action2, BookStateEnum.WYPOZYCZONA);
        bookState2.setBook(book1);
        bookState2.setAction(action2);
        bookStateRepository.save(bookState2);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        BookState bookState3 = TestUtils.createBookState(action1, BookStateEnum.WYPOZYCZONA);
        bookState3.setDateOfReturn(LocalDate.now().plusDays(11));
        bookState3.setDateOfLoan(LocalDate.now().plusDays(11));
        bookState3.setBook(book2);
        bookState3.setAction(action1);
        bookStateRepository.save(bookState3);

        BookState bookState4 = TestUtils.createBookState(action2, BookStateEnum.ZWROCONA);
        /*** //ustawienie starszej daty sprawa, że bs2 jest "nowsze" od bs3 i metoda bookStateRepository.findBookStateByBook wybierze nowszego BookState'a, czyli bookState2*/
        bookState4.setDateOfReturn(LocalDate.now().minusDays(1));
        bookState4.setBook(book2);
        bookState4.setAction(action2);
        bookStateRepository.save(bookState4);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        BookState bookState1 = TestUtils.createBookState(action1, BookStateEnum.WYPOZYCZONA);
        bookState1.setBook(book1);
        bookState1.setAction(action1);
        bookStateRepository.save(bookState1);

        BookState bookState5 = TestUtils.createBookState(action1, BookStateEnum.ZNISZCZONA);
        bookState5.setDateOfReturn(LocalDate.now().plusDays(1));
        bookState5.setBook(book1);
        bookState5.setAction(action1);
        bookStateRepository.save(bookState5);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertTrue(isBookLoanable.isBookAbleToLoan(book));
        assertFalse(isBookLoanable.isBookAbleToLoan(book2));
        assertFalse(isBookLoanable.isBookAbleToLoan(book1));
    }
}
