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


/**
 * @DataJpaTest
 * @AutoConfigureTestDatabase(replace=Replace.NONE)
 * adnotacje do przetestowania
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ActionRepoQuerryTest extends RepositoriesClass{

    @Autowired
    public final JdbcTemplate jdbcTemplate=null;


    @After
    public void after() {
        jdbcTemplate.update("delete from actions");
        jdbcTemplate.update("delete from books");
        jdbcTemplate.update("delete from author");
        jdbcTemplate.update("delete from user");
    }

    //test passed! obiekty są prawidłowo usuwane z bazy po teście
    @Test
    public void actionTest() {

        Author author = TestUtils.createAuthor();
        this.getAuthorRepository().save(author);

        Book book = TestUtils.createBook(author);
        this.getBookRepository().save(book);

        User user = TestUtils.createUser();
        this.getUserRepository().save(user);

        Action action = TestUtils.createAction(book, user);
        action.setActionDescription("x");
        action.setBook(book);
        action.setUser(user);
        this.getActionRepository().save(action);

        Action action1 = this.getActionRepository().getOne(action.getId());

        assertNotNull(action);
        assertEquals(action1.getId(), action.getId());

        List<Action> actionList = this.getActionRepository().findActionsWithDestroyedBooksByUser(user);

        assertFalse(this.getActionRepository().findActionByActionDescription("x").isEmpty());
        assertFalse(this.getActionRepository().findActionByBook(book).isEmpty());
        assertTrue(actionList.isEmpty());
    }
}
