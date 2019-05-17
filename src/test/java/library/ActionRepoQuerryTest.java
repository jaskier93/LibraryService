package library;

import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.repositories.*;
import library.users.User;
import org.junit.After;
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
    public final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final AuthorRepository authorRepository = null;


    @After
    public void after() {
       // jdbcTemplate.update("delete a from Action  a where  a.actionDescription='xxxyyyzzz'");
        jdbcTemplate.update("delete from books");
        jdbcTemplate.update("delete from author");
        jdbcTemplate.update("delete from user");
    }

    //test passed! obiekty są prawidłowo usuwane z bazy po teście
    @Test
    public void actionTest() {
        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = TestUtils.createAction(book, user);
        //  action.setActionDescription("x");
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        Action action1 = actionRepository.getOne(action.getId());

        assertNotNull(action);
        assertEquals(action1.getId(), action.getId());

        List<Action> actionList = actionRepository.findActionsWithDestroyedBooksByUser(user);

        assertFalse(actionRepository.findActionByActionDescription("xxxyyyzzz").isEmpty());
        assertFalse(actionRepository.findActionByBook(book).isEmpty());
        assertTrue(actionList.isEmpty());
    }
}
