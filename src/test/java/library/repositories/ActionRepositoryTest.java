package library.repositories;

import library.TestUtils;
import library.enums.ActionDescription;
import library.models.Action;
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
public class ActionRepositoryTest {

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    //test passed!
    @Test
    public void actionTest() {
        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = TestUtils.createAction(book, user);
        actionRepository.save(action);

        Action action1 = actionRepository.getOne(action.getId());

        assertNotNull(action);
        assertEquals(action1.getId(), action.getId());

        List<Action> actionList = actionRepository.findActionsWithDestroyedBooksByUser(user);

        assertFalse(actionRepository.findActionByActionDescription(ActionDescription.TEST).isEmpty()); // TODO: sprawdzić czy działa
        assertTrue(actionRepository.findActionByActionDescription(ActionDescription.ZWROT).isEmpty());
        assertFalse(actionRepository.findActionByBook(book).isEmpty());
        assertTrue(actionList.isEmpty());
    }
}
