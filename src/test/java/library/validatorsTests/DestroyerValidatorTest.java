package library.validatorsTests;

import library.TestUtils;
import library.enums.ActionDescription;
import library.models.Action;
import library.models.Book;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
import library.users.User;
import library.validators.DestroyerValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DestroyerValidatorTest {

    @Autowired
    private final DestroyerValidator destroyerValidator = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    @Test //test passed! prawidłowo usuwa obiekty (akcje z enumem=TEST również)
    public void validatorTest() {

        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);
        Book book1 = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book1);
        Book book2 = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book2);

        User user = TestUtils.createUser();
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        userRepository.save(user2);

        User user3 = TestUtils.createUser();
        userRepository.save(user3);

        Action action = TestUtils.createAction(book, user);
        action.setUser(user);
        action.setBook(book);
        action.setActionDescription(ActionDescription.ZNISZCZENIE);
        actionRepository.save(action);

        Action action1 = TestUtils.createAction(book1, user);
        action1.setUser(user);
        action1.setBook(book1);
        action1.setActionDescription(ActionDescription.ZNISZCZENIE);
        actionRepository.save(action1);

        Action action2 = TestUtils.createAction(book2, user3);
        action2.setUser(user3);
        action2.setActionDescription(ActionDescription.ZWROT);
        action2.setBook(book2);
        actionRepository.save(action2);

        assertTrue(destroyerValidator.validator(user));
        assertFalse(destroyerValidator.validator(user2));
        assertFalse(destroyerValidator.validator(user2));

        action.setActionDescription(ActionDescription.TEST);
        actionRepository.save(action);
        action1.setActionDescription(ActionDescription.TEST);
        actionRepository.save(action1);
        action2.setActionDescription(ActionDescription.TEST);
        actionRepository.save(action2);
    }
}
