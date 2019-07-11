package library.services.rankingServices;

import library.TestUtils;
import library.enums.ActionDescription;
import library.enums.Category;
import library.enums.StatusRekordu;
import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.repositories.*;
import library.models.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @Autowired
    private final CategoryRankingService categoryRankingService = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from book_states where status=1020304050");
        jdbcTemplate.update("Delete from actions where action_description ='TEST' or status_rekordu='TEST'");
        jdbcTemplate.update("delete from books where status_rekordu='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from user where email='example@gmail.com'");
    }

    @Test
    public void topLoanedBooksByAgeCategoryTest() {

        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        List<Book> bookList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = TestUtils.createUser();
            user.setLastName("Kowalski" + i);
            user.setAdmin(true);
            userList.add(user);
            userRepository.save(user);
        }

        for (int i = 0; i < 20; i++) {
            Book book = TestUtils.createBook(author);
            book.setTitle("Wiedźmin" + i);
            book.setPages(500 + i);
            bookList.add(book);
            bookList.get(i % 10).setCategory(Category.THRILLER);
            bookRepository.save(book);
        }
        for (int i = 0; i < 60; i++) {
            Action action = TestUtils.createAction(bookList.get(i % 20), userList.get(i % 10));
            action.setActionDescription(ActionDescription.WYPOZYCZENIE);
            action.setStatusRekordu(StatusRekordu.ACTIVE);
            actionList.add(action);
            actionRepository.save(action);
        }

        Action action = TestUtils.createAction(bookList.get(10), userList.get(0));
        action.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action);
        actionRepository.save(action);
        Action action1 = TestUtils.createAction(bookList.get(11), userList.get(0));
        action1.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action1.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action1);
        actionRepository.save(action1);
        Action action2 = TestUtils.createAction(bookList.get(11), userList.get(1));
        action2.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action2.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action2);
        actionRepository.save(action2);
        Action action3 = TestUtils.createAction(bookList.get(13), userList.get(1));
        action3.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action3.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action3);
        actionRepository.save(action3);
        Action action4 = TestUtils.createAction(bookList.get(10), userList.get(1));
        action4.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action4.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action4);
        actionRepository.save(action4);
        Action action5 = TestUtils.createAction(bookList.get(11), userList.get(2));
        action5.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action5.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action5);
        actionRepository.save(action5);
        Action action6 = TestUtils.createAction(bookList.get(16), userList.get(3));
        action6.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action6.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action6);
        actionRepository.save(action6);
        Action action7 = TestUtils.createAction(bookList.get(11), userList.get(4));
        action7.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action7.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action7);
        actionRepository.save(action7);
        Action action8 = TestUtils.createAction(bookList.get(16), userList.get(5));
        action8.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action8.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action8);
        actionRepository.save(action8);
        Action action9 = TestUtils.createAction(bookList.get(10), userList.get(5));
        action9.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action9.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action9);
        actionRepository.save(action9);


        Action action10 = TestUtils.createAction(bookList.get(1), userList.get(0));
        action10.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action10.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action10);
        actionRepository.save(action10);
        Action action11 = TestUtils.createAction(bookList.get(1), userList.get(1));
        action11.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action11.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action11);
        actionRepository.save(action11);
        Action action12 = TestUtils.createAction(bookList.get(2), userList.get(1));
        action12.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action12.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action12);
        actionRepository.save(action12);
        Action action13 = TestUtils.createAction(bookList.get(2), userList.get(1));
        action13.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action13.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action13);
        actionRepository.save(action13);
        Action action14 = TestUtils.createAction(bookList.get(2), userList.get(2));
        action14.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action14.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action14);
        actionRepository.save(action14);
        Action action15 = TestUtils.createAction(bookList.get(2), userList.get(3));
        action15.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action15.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action15);
        actionRepository.save(action15);
        Action action16 = TestUtils.createAction(bookList.get(3), userList.get(4));
        action16.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action16.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action16);
        actionRepository.save(action16);
        Action action17 = TestUtils.createAction(bookList.get(3), userList.get(5));
        action17.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action17.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action17);
        actionRepository.save(action17);
        Action action18 = TestUtils.createAction(bookList.get(3), userList.get(5));
        action18.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action18.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action18);
        actionRepository.save(action18);
        Action action19 = TestUtils.createAction(bookList.get(5), userList.get(0));
        action19.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action19.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action19);
        actionRepository.save(action19);

        List<Book> topThrillers = new ArrayList<>();
        topThrillers.add(bookList.get(2));
        topThrillers.add(bookList.get(3));
        topThrillers.add(bookList.get(1));
        topThrillers.add(bookList.get(5));
        topThrillers.add(bookList.get(0));
        topThrillers.add(bookList.get(4));
        topThrillers.add(bookList.get(6));
        topThrillers.add(bookList.get(7));
        topThrillers.add(bookList.get(8));
        topThrillers.add(bookList.get(9));

        List<Book> topAdventures = new ArrayList<>();
        topAdventures.add(bookList.get(11));
        topAdventures.add(bookList.get(10));
        topAdventures.add(bookList.get(16));
        topAdventures.add(bookList.get(13));
        topAdventures.add(bookList.get(12));
        topAdventures.add(bookList.get(14));
        topAdventures.add(bookList.get(15));
        topAdventures.add(bookList.get(17));
        topAdventures.add(bookList.get(18));
        topAdventures.add(bookList.get(19));


        assertEquals(topAdventures, categoryRankingService.topLoanedBooksByCategory(Category.ADVENTURE));
        assertEquals(topThrillers, categoryRankingService.topLoanedBooksByCategory(Category.THRILLER));
        assertTrue(categoryRankingService.topLoanedBooksByCategory(Category.GUIDE).isEmpty());

        for (int i = 0; i <actionList.size() ; i++) {
            actionList.get(i).setActionDescription(ActionDescription.TEST);
            actionList.get(i).setStatusRekordu(StatusRekordu.TEST);
            actionRepository.save(actionList.get(i));
        }
    }
}