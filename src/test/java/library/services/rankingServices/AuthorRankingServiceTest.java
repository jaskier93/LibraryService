package library.services.rankingServices;

import library.TestUtils;
import library.enums.ActionDescription;
import library.enums.AgeCategory;
import library.enums.Category;
import library.enums.StatusRekordu;
import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.repositories.ActionRepository;
import library.repositories.AuthorRepository;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
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
public class AuthorRankingServiceTest {

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
    private final AuthorRankingService authorRankingService = null;

    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST' or status_rekordu='TEST'");
        jdbcTemplate.update("delete from books where status_rekordu='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'  or status_rekordu='TEST'");
        jdbcTemplate.update("delete from user where email='example@gmail.com'  or status_rekordu='TEST'");
    }

    @Test
    public void authorRankingServiceMethodsTest() {

        User user = TestUtils.createUser();
        userRepository.save(user);

        List<Book> bookList = new ArrayList<>();
        List<Author> authorList = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            Author author = TestUtils.createAuthor();
            author.setLastName("Sapkowski" + i);
            authorList.add(author);
            authorRepository.save(author);
        }

        for (int i = 0; i < 60; i++) {
            Book book = TestUtils.createBook(authorList.get(i % 12));
            book.setPages(300 + i * 10);
            bookList.add(book);
            bookRepository.save(book);

            Action action = TestUtils.createAction(bookList.get(i % 12), user);
            action.setActionDescription(ActionDescription.WYPOZYCZENIE);
            action.setStatusRekordu(StatusRekordu.ACTIVE);
            actionList.add(action);
            actionRepository.save(action);
        }

        Action action = TestUtils.createAction(bookList.get(0), user);
        action.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action);
        actionRepository.save(action);
        Action action1 = TestUtils.createAction(bookList.get(0), user);
        action1.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action1.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action1);
        actionRepository.save(action1);
        Action action2 = TestUtils.createAction(bookList.get(0), user);
        action2.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action2.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action2);
        actionRepository.save(action2);
        Action action3 = TestUtils.createAction(bookList.get(2), user);
        action3.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action3.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action3);
        actionRepository.save(action3);
        Action action4 = TestUtils.createAction(bookList.get(2), user);
        action4.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action4.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action4);
        actionRepository.save(action4);
        Action action5 = TestUtils.createAction(bookList.get(2), user);
        action5.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action5.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action5);
        actionRepository.save(action5);
        Action action6 = TestUtils.createAction(bookList.get(2), user);
        action6.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action6.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action6);
        actionRepository.save(action6);
        Action action7 = TestUtils.createAction(bookList.get(2), user);
        action7.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action7.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action7);
        actionRepository.save(action7);
        Action action8 = TestUtils.createAction(bookList.get(0), user);
        action8.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action8.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action8);
        actionRepository.save(action8);
        Action action9 = TestUtils.createAction(bookList.get(1), user);
        action9.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action9.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action9);
        actionRepository.save(action9);
        Action action10 = TestUtils.createAction(bookList.get(1), user);
        action10.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action10.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action10);
        actionRepository.save(action10);
        Action action11 = TestUtils.createAction(bookList.get(1), user);
        action11.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action11.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action11);
        actionRepository.save(action11);
        Action action12 = TestUtils.createAction(bookList.get(3), user);
        action12.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action12.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action12);
        actionRepository.save(action12);
        Action action13 = TestUtils.createAction(bookList.get(4), user);
        action13.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action13.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action13);
        actionRepository.save(action13);
        Action action14 = TestUtils.createAction(bookList.get(5), user);
        action14.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action14.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action14);
        actionRepository.save(action14);
        Action action15 = TestUtils.createAction(bookList.get(6), user);
        action15.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action15.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action15);
        actionRepository.save(action15);
        Action action16 = TestUtils.createAction(bookList.get(7), user);
        action16.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action16.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action16);
        actionRepository.save(action16);
        Action action17 = TestUtils.createAction(bookList.get(5), user);
        action17.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action17.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action17);
        actionRepository.save(action17);
        Action action18 = TestUtils.createAction(bookList.get(4), user);
        action18.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action18.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action18);
        actionRepository.save(action18);
        Action action19 = TestUtils.createAction(bookList.get(8), user);
        action19.setActionDescription(ActionDescription.WYPOZYCZENIE);
        action19.setStatusRekordu(StatusRekordu.ACTIVE);
        actionList.add(action19);
        actionRepository.save(action19);

        List<Author> expectedTopAuthorsByLoansQuantity = new ArrayList<>();
        expectedTopAuthorsByLoansQuantity.add(authorList.get(2));
        expectedTopAuthorsByLoansQuantity.add(authorList.get(0));
        expectedTopAuthorsByLoansQuantity.add(authorList.get(1));
        expectedTopAuthorsByLoansQuantity.add(authorList.get(4));
        expectedTopAuthorsByLoansQuantity.add(authorList.get(5));
        expectedTopAuthorsByLoansQuantity.add(authorList.get(3));
        expectedTopAuthorsByLoansQuantity.add(authorList.get(6));
        expectedTopAuthorsByLoansQuantity.add(authorList.get(7));
        expectedTopAuthorsByLoansQuantity.add(authorList.get(8));
        expectedTopAuthorsByLoansQuantity.add(authorList.get(10));

        List<Author> expectedTopAuthorsBySumOfBookPages = new ArrayList<>();
        expectedTopAuthorsBySumOfBookPages.add(authorList.get(2));
        expectedTopAuthorsBySumOfBookPages.add(authorList.get(0));
        expectedTopAuthorsBySumOfBookPages.add(authorList.get(1));
        expectedTopAuthorsBySumOfBookPages.add(authorList.get(5));
        expectedTopAuthorsBySumOfBookPages.add(authorList.get(4));
        expectedTopAuthorsBySumOfBookPages.add(authorList.get(8));
        expectedTopAuthorsBySumOfBookPages.add(authorList.get(7));
        expectedTopAuthorsBySumOfBookPages.add(authorList.get(6));
        expectedTopAuthorsBySumOfBookPages.add(authorList.get(11));
        expectedTopAuthorsBySumOfBookPages.add(authorList.get(10));

        assertEquals(80, actionList.size());
        assertEquals(expectedTopAuthorsByLoansQuantity, authorRankingService.topAuthorsByLoansQuantity());
        assertEquals(expectedTopAuthorsByLoansQuantity, authorRankingService.topAgeCategoryAuthorsByLoansQuantity(AgeCategory.DOROSLI));
        assertEquals(expectedTopAuthorsByLoansQuantity, authorRankingService.topCategoryAuthorsByLoansQuantity(Category.ADVENTURE));
        assertEquals(0, authorRankingService.topCategoryAuthorsByLoansQuantity(Category.THRILLER).size());
        assertEquals(0, authorRankingService.topAgeCategoryAuthorsByLoansQuantity(AgeCategory.NAJMLODSI).size());
        assertEquals(expectedTopAuthorsBySumOfBookPages, authorRankingService.topAuthorsBySumOfBookPages());

        for (int i = 0; i < actionList.size(); i++) {
            actionList.get(i).setActionDescription(ActionDescription.TEST);
            actionList.get(i).setStatusRekordu(StatusRekordu.TEST);
            actionRepository.save(actionList.get(i));
        }
    }
}