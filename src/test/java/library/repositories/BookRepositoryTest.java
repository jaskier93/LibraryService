package library.repositories;

import library.TestUtils;
import library.enums.AgeCategory;
import library.enums.BookStateEnum;
import library.enums.Category;
import library.models.Action;
import library.models.Author;
import library.models.Book;
import library.models.BookState;
import library.models.User;
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
public class BookRepositoryTest {

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final BookStateRepository bookStateRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where status_rekordu='TEST'");
        jdbcTemplate.update("delete from user where status_rekordu='TEST'");
        jdbcTemplate.update("delete from book_states where status=1020304050");
    }

    //test passed!
    @Test
    public void bookRepositoryTest() {

        LocalDate today = LocalDate.now();
        LocalDateTime todayTime = LocalDateTime.now();

        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Book book = TestUtils.createBook(author);
        book.setReleaseDate(book.getReleaseDate().minusDays(50));
        book.setCreated(book.getCreated().plusDays(50));
        bookRepository.save(book);

        Book book1 = TestUtils.createBook(author);
        book1.setReleaseDate(LocalDate.now());
        bookRepository.save(book1);

        Book book2 = TestUtils.createBook(author);
        book2.setReleaseDate(book2.getReleaseDate().minusDays(30));
        book2.setCreated(book2.getCreated().plusDays(30));
        bookRepository.save(book2);

        User user = TestUtils.createUser();
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        userRepository.save(user2);

        Book bookFromBase = bookRepository.getOne(book.getId());
        assertEquals(book.getId(), bookFromBase.getId());

        List<Book> bookListOrderByReleaseDate = new ArrayList<>();
        bookListOrderByReleaseDate.add(book1);
        bookListOrderByReleaseDate.add(book2);
        bookListOrderByReleaseDate.add(book);

        List<Book> bookListOrderedByAddingDate = new ArrayList<>();
        bookListOrderedByAddingDate.add(book1);
        bookListOrderedByAddingDate.add(book2);
        bookListOrderedByAddingDate.add(book);

        Action action = TestUtils.createAction(book, user);
        actionRepository.save(action);

        Action action4 = TestUtils.createAction(book, user2);
        actionRepository.save(action4);

        BookState bookState = TestUtils.createBookState(action, BookStateEnum.ZWROCONA);
        bookState.setBook(book);
        bookState.setAction(action);
        bookStateRepository.save(bookState);

        BookState bookState1 = TestUtils.createBookState(action, BookStateEnum.ZWROCONA);
        bookState1.setBook(book);
        bookState1.setDateFrom(today.plusDays(10));
        bookState1.setDateTo(today.plusDays(30));
        bookState1.setAction(action);
        bookStateRepository.save(bookState1);

        BookState bookState2 = TestUtils.createBookState(action, BookStateEnum.ZWROCONA);
        bookState2.setBook(book);
        bookState2.setDateTo(today.plusDays(40));
        bookState2.setDateFrom(today.plusDays(50));
        bookState2.setAction(action);
        bookStateRepository.save(bookState2);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        BookState bookState3 = TestUtils.createBookState(action4, BookStateEnum.WYPOZYCZONA);
        bookState3.setBook(book);
        bookState3.setAction(action4);
        bookStateRepository.save(bookState3);

        BookState bookState4 = TestUtils.createBookState(action4, BookStateEnum.WYPOZYCZONA);
        bookState4.setBook(book);
        bookState4.setDateFrom(today.plusDays(130));
        bookState4.setAction(action4);
        bookStateRepository.save(bookState4);

        BookState bookState5 = TestUtils.createBookState(action4, BookStateEnum.WYPOZYCZONA);
        bookState5.setBook(book);
        bookState5.setDateFrom(today.plusDays(50));
        bookState5.setAction(action4);
        bookStateRepository.save(bookState5);

        List<Book> loanedBookList = new ArrayList<>();
        loanedBookList.add(book);

        assertFalse(bookRepository.findBookByTitle("WiedźminWiedźmin").isEmpty());
        assertEquals("WiedźminWiedźmin", book2.getTitle());
        assertNotEquals("sdfssdffds", book2.getTitle());
        assertEquals(book2.getAgeCategory(), AgeCategory.DOROSLI);
        assertNotEquals(book2.getAgeCategory(), AgeCategory.NAJMLODSI);
        assertEquals(book2.getCategory(), Category.ADVENTURE);
        assertNotEquals(book2.getCategory(), Category.DICTIONARY);
        assertFalse(bookRepository.findAllBooksByAgeCategory(AgeCategory.DOROSLI).isEmpty());
        assertTrue(bookRepository.findAllBooksByAgeCategory(AgeCategory.NAJMLODSI).isEmpty());
        assertFalse(bookRepository.findAllBooksByCategory(Category.ADVENTURE).isEmpty());
        assertTrue(bookRepository.findAllBooksByCategory(Category.THRILLER).isEmpty());

        //test ma sprawdzić, czy w przeciągu określonego czasu, zostały dodane jakieś książki do biblioteki
        assertFalse(bookRepository.booksAddedInPeriod(today.minusDays(111)).isEmpty());
        assertTrue(bookRepository.booksAddedInPeriod(today.plusDays(111)).isEmpty());

        //test ma sprawdzić, czy w  bibliotece są ksiązki wydane w przeciągu określonego czasu
        assertFalse(bookRepository.booksReleasedInPeriod(todayTime.minusYears(10)).isEmpty());
        assertTrue(bookRepository.booksReleasedInPeriod(todayTime.plusYears(10)).isEmpty());

        //test ma na celu sprawdzić, czy książki są posortowane prawidłowo po dacie wydania (od najstarszej)
        assertEquals(bookListOrderByReleaseDate, bookRepository.sortedBooksByReleaseData());

        //test ma na celu sprawdzić, czy książki są posortowane prawidłowo po dacie dodania do biblioteki (od najstarszej)
        assertEquals(bookListOrderedByAddingDate, bookRepository.sortedBooksByAddingData());

        assertEquals(loanedBookList, bookRepository.findLoanedBooksByUser(user2));

        //DISTINCT wybiera tylko unikalne obiekty (książki)
        assertEquals(1, bookRepository.findBookByAgeCategory(AgeCategory.DOROSLI).size());

        assertEquals(1, bookRepository.findBookByCategory(Category.ADVENTURE).size());
        assertTrue(bookRepository.findBookByCategory(Category.SCIENCE).isEmpty());
        assertEquals(0, bookRepository.findBookByCategory(Category.DRAMA).size());

        assertEquals(Integer.valueOf(1500), bookRepository.sumPagesForUser(user2));
    }
}

