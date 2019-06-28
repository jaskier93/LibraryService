package library.services.rankingServices;

import library.TestUtils;
import library.enums.ActionDescription;
import library.enums.BookStateEnum;
import library.enums.StatusRekordu;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRankingServiceTest {

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final BookStateRepository bookStateRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @Autowired
    private final UserRankingService userRankingService = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from book_states where status=1020304050");
        jdbcTemplate.update("Delete from actions where action_description ='TEST' or status_rekordu='TEST'");
        jdbcTemplate.update("delete from books where status_rekordu='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");

    }

    @Test
    public void userRankingMethodsTest() {
        LocalDate today = LocalDate.now();

        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        List<Book> bookList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();
        List<BookState> bookStateList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = TestUtils.createUser();
            user.setLastName("Kowalski" + i);
            user.setAdmin(true);
            userList.add(user);
            userRepository.save(user);

            Book book = TestUtils.createBook(author);
            book.setTitle("Wiedźmin" + i);
            book.setPages(500 + bookList.size() + 1);
            bookList.add(book);
            bookRepository.save(book);
        }

        for (int i = 0; i < 40; i++) {

            Book book = TestUtils.createBook(author);
            book.setTitle("Wiedźmin" + i);
            book.setPages(500 + i);
            bookList.add(book);
            bookRepository.save(book);

            Action action = TestUtils.createAction(bookList.get(i), userList.get(i % 10));
            action.setActionDescription(ActionDescription.WYPOZYCZENIE);
            action.setStatusRekordu(StatusRekordu.ACTIVE);
            actionList.add(action);
            actionRepository.save(action);

            BookState bookState = TestUtils.createBookState(actionList.get(i), BookStateEnum.WYPOZYCZONA);
            bookStateList.add(bookState);
            bookStateRepository.save(bookState);
        }


        Action action = TestUtils.createAction(bookList.get(40), userList.get(0));
        action.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action);
        actionRepository.save(action);
        Action action1 = TestUtils.createAction(bookList.get(41), userList.get(0));
        action1.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action1.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action1);
        actionRepository.save(action1);
        Action action2 = TestUtils.createAction(bookList.get(42), userList.get(0));
        action2.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action2.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action2);
        actionRepository.save(action2);
        Action action3 = TestUtils.createAction(bookList.get(43), userList.get(1));
        action3.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action3.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action3);
        actionRepository.save(action3);
        Action action4 = TestUtils.createAction(bookList.get(44), userList.get(1));
        action4.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action4.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action4);
        actionRepository.save(action4);
        Action action5 = TestUtils.createAction(bookList.get(45), userList.get(2));
        action5.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action5.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action5);
        actionRepository.save(action5);
        Action action6 = TestUtils.createAction(bookList.get(46), userList.get(3));
        action6.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action6.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action6);
        actionRepository.save(action6);
        Action action7 = TestUtils.createAction(bookList.get(47), userList.get(4));
        action7.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action7.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action7);
        actionRepository.save(action7);
        Action action8 = TestUtils.createAction(bookList.get(48), userList.get(5));
        action8.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action8.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action8);
        actionRepository.save(action8);
        Action action9 = TestUtils.createAction(bookList.get(49), userList.get(5));
        action9.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action9.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action9);
        actionRepository.save(action9);

        for (int i = 0; i < 10; i++) {
            BookState bookState = TestUtils.createBookState(actionList.get(i + 40), BookStateEnum.WYPOZYCZONA);
            bookState.setDateOfReturn(today.plusDays(i));
            bookStateList.add(bookState);
            bookStateRepository.save(bookState);
        }

        List<User> predictedTopLoansQuantityByUserRankingList = new ArrayList<>();
        predictedTopLoansQuantityByUserRankingList.add(userList.get(0));
        predictedTopLoansQuantityByUserRankingList.add(userList.get(1));
        predictedTopLoansQuantityByUserRankingList.add(userList.get(5));
        predictedTopLoansQuantityByUserRankingList.add(userList.get(2));
        predictedTopLoansQuantityByUserRankingList.add(userList.get(3));
        predictedTopLoansQuantityByUserRankingList.add(userList.get(4));
        predictedTopLoansQuantityByUserRankingList.add(userList.get(6));
        predictedTopLoansQuantityByUserRankingList.add(userList.get(7));
        predictedTopLoansQuantityByUserRankingList.add(userList.get(8));
        predictedTopLoansQuantityByUserRankingList.add(userList.get(9));

        List<User> predictedTopSumPagesByUserRankingList = new ArrayList<>();
        predictedTopSumPagesByUserRankingList.add(userList.get(0));
        predictedTopSumPagesByUserRankingList.add(userList.get(5));
        predictedTopSumPagesByUserRankingList.add(userList.get(1));
        predictedTopSumPagesByUserRankingList.add(userList.get(4));
        predictedTopSumPagesByUserRankingList.add(userList.get(3));
        predictedTopSumPagesByUserRankingList.add(userList.get(2));
        predictedTopSumPagesByUserRankingList.add(userList.get(9));
        predictedTopSumPagesByUserRankingList.add(userList.get(8));
        predictedTopSumPagesByUserRankingList.add(userList.get(7));
        predictedTopSumPagesByUserRankingList.add(userList.get(6));

        assertEquals(50, bookList.size());
        assertEquals(50, actionList.size());
        assertEquals(50, bookStateList.size());
        assertEquals(predictedTopLoansQuantityByUserRankingList, userRankingService.topUsersByLoansQuantity());
        assertEquals(predictedTopSumPagesByUserRankingList, userRankingService.topUsersBySumOfBooksPages());

        for (int i = 0; i < 50; i++) {
            actionList.get(i).setStatusRekordu(StatusRekordu.TEST);
            actionList.get(i).setActionDescription(ActionDescription.TEST);
            actionRepository.save(actionList.get(i));
        }
    }
}

































