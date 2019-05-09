package library;

import library.models.Action;
import library.models.Book;
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
public class ActionRepoQuerryTest {

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Test
    public void actionTest() {
        Book book = TestUtils.createBook();
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = TestUtils.createAction(book, user);
        actionRepository.save(action);

        Action action1 = actionRepository.getOne(action.getId());

        assertNotNull(action);
        assertNotNull(actionRepository);
        assertEquals(action1.getId(), action.getId());

        assertEquals(null, actionRepository.findActionWithDestroyedBooksByUser(user));
        assertEquals(1, actionRepository.findActionByActionDescription("x").size());
        assertEquals(false, actionRepository.findActionByBook(book).isEmpty());
        assertEquals(false, actionRepository.findActionWithDestroyedBooksByUser(user));

        actionRepository.delete(action);
        userRepository.delete(user);
        bookRepository.delete(book);
    }
}
