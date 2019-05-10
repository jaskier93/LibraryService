package library;

import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.repositories.*;
import library.users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ActionRepoQuerryTest {

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    //test passed!
    @Test
    public void actionTest() {
        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Book book = TestUtils.createBook();
        book.setAuthor(author);
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = TestUtils.createAction(book, user);
        action.setActionDescription("x");
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        Action action1 = actionRepository.getOne(action.getId());

        assertNotNull(action);
        assertEquals(action1.getId(), action.getId());

        List<Action> actionList = actionRepository.findActionsWithDestroyedBooksByUser(user);

       // assertNull(actionList);
        assertFalse(actionRepository.findActionByActionDescription("x").isEmpty());
        assertFalse(actionRepository.findActionByBook(book).isEmpty());
        assertTrue(actionList.isEmpty());

        actionRepository.delete(action);
        userRepository.delete(user);
        bookRepository.delete(book);
        authorRepository.delete(author);
    }
}
