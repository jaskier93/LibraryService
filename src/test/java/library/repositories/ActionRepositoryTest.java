package library.repositories;

import library.TestUtils;
import library.enums.ActionDescription;
import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.models.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
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

    @Autowired
    private final AuthorRepository authorRepository = null;


    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
        jdbcTemplate.update("delete from actions");
    }

    //test passed!
    @Test
    public void actionRepositoryTest() {
        LocalDateTime testDate = LocalDateTime.now().minusDays(3);
        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Book book = TestUtils.createBook(author);
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        Action action = TestUtils.createAction(book, user);
        action.setCreated(LocalDateTime.now().minusDays(5));
        actionRepository.save(action);

        Action action1 = TestUtils.createAction(book, user);
        action1.setCreated(LocalDateTime.now().minusDays(1));
        actionRepository.save(action1);
       

        Action action2 = actionRepository.getOne(action.getId());

        assertNotNull(action);
        assertEquals(action2.getId(), action.getId());

        List<Action> actionList = actionRepository.findActionsWithDestroyedBooksByUser(user);


        assertFalse(actionRepository.findActionByActionDescription(ActionDescription.TEST).isEmpty());
        assertTrue(actionRepository.findActionByActionDescription(ActionDescription.ZWROT).isEmpty());
        assertFalse(actionRepository.findActionByBook(book).isEmpty());
        assertTrue(actionList.isEmpty());
        assertEquals(1, actionRepository.findNewestAction(user, ActionDescription.TEST, testDate).size());

    }
}
