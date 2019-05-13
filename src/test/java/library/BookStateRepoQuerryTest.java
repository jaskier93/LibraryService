package library;

import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.models.BookState;
import library.repositories.*;
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
public class BookStateRepoQuerryTest {

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final BookStateRepository bookStateRepository = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @After
    public void after() {
        //     jdbcTemplate.update("delete from actions");
        //   jdbcTemplate.update("delete from books");
        // jdbcTemplate.update("delete from author");
        //jdbcTemplate.update("delete from user");
        jdbcTemplate.update("delete from book_states");
    }


    @Test
    public void bsTest() {

        Book book = TestUtils.createBook();
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = TestUtils.createAction(book, user);

        BookState bookState = TestUtils.createBookState(book, action, BookStateEnum.NOWA);
/*
    Action action = TestUtils.createAction(TestUtils.createBook(), TestUtils.createUser());

    BookState bookState = TestUtils.createBookState(TestUtils.createBook(),  TestUtils.createAction(TestUtils.createBook(), TestUtils.createUser()), BookStateEnum.NOWA);*/

        /**
         *messageTemplate='{javax.validation.constraints.NotNull.message}
         *Validation failed for classes [library.models.BookState] during persist time for groups
         */
        bookStateRepository.save(bookState);
        BookState bookState1 = bookStateRepository.getOne(bookState.getId());

        assertFalse(bookStateRepository.findBooksByUser(TestUtils.createUser()).isEmpty());
        assertNotNull(bookStateRepository.findBookStateByBook(TestUtils.createBook().getId()));
        assertEquals(bookState.getId(), bookState1.getId());
        assertNotNull(bookState);

    }

}
