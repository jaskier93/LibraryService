package library.validators.mainValidators;

import library.TestUtils;
import library.enums.ActionDescription;
import library.models.Action;
import library.models.Book;
import library.repositories.*;
import library.users.User;
import library.validators.mainValidators.OverdueReturnBookAmountValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
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
public class OverdueReturnBookAmountValidatorTest {

    @Autowired
    private final OverdueReturnBookAmountValidator overdueReturnBookAmountValidator = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    @Test
    public void hasUserMoreThan5OverdueReturns() {
        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);

        User user = TestUtils.createUser();
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        userRepository.save(user2);

        Action action = TestUtils.createAction(book, user);
        action.setActionDescription(ActionDescription.PRZETERMINOWANIE);
        action.setBook(book);
        action.setUser(user);
        actionRepository.save(action);

        Action action1 = TestUtils.createAction(book, user);
        action1.setActionDescription(ActionDescription.PRZETERMINOWANIE);
        action1.setBook(book);
        action1.setUser(user);
        actionRepository.save(action1);

        Action action2 = TestUtils.createAction(book, user);
        action2.setActionDescription(ActionDescription.PRZETERMINOWANIE);
        action2.setBook(book);
        action2.setUser(user);
        actionRepository.save(action2);

        Action action3 = TestUtils.createAction(book, user);
        action3.setActionDescription(ActionDescription.PRZETERMINOWANIE);
        action3.setBook(book);
        action3.setUser(user);
        actionRepository.save(action3);

        Action action4 = TestUtils.createAction(book, user);
        action4.setActionDescription(ActionDescription.PRZETERMINOWANIE);
        action4.setBook(book);
        action4.setUser(user);
        actionRepository.save(action4);

        Action action5 = TestUtils.createAction(book, user);
        action5.setActionDescription(ActionDescription.PRZETERMINOWANIE);
        action5.setBook(book);
        action5.setUser(user);
        actionRepository.save(action5);

        Action action6 = TestUtils.createAction(book, user2);
        action6.setActionDescription(ActionDescription.PRZETERMINOWANIE);
        action6.setBook(book);
        action6.setUser(user2);
        actionRepository.save(action6);

        assertNotNull(actionRepository.findActionByActionDescription(ActionDescription.PRZETERMINOWANIE));
        assertTrue(overdueReturnBookAmountValidator.validator(user));
        assertFalse(overdueReturnBookAmountValidator.validator(user2));

        action.setActionDescription(ActionDescription.TEST);
        actionRepository.save(action);

        action1.setActionDescription(ActionDescription.TEST);
        actionRepository.save(action1);

        action2.setActionDescription(ActionDescription.TEST);
        actionRepository.save(action2);

        action3.setActionDescription(ActionDescription.TEST);
        actionRepository.save(action3);

        action4.setActionDescription(ActionDescription.TEST);
        actionRepository.save(action4);

        action5.setActionDescription(ActionDescription.TEST);
        actionRepository.save(action5);

        action6.setActionDescription(ActionDescription.TEST);
        actionRepository.save(action6);
    }
}