package library.repositories;

import library.TestUtils;
import library.enums.AgeCategory;
import library.enums.BookStateEnum;
import library.enums.Category;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
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

    @After
    public void after() {
        jdbcTemplate.update("Delete from actions where action_description ='TEST'");
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
        jdbcTemplate.update("delete from book_states where status=1020304050");
    }

    //test passed!
    @Test
    public void bookRepositoryTest() {

        LocalDate today = LocalDate.now();
        LocalDateTime todayTime = LocalDateTime.now();

        Book book = TestUtils.createBook(TestUtils.createAuthor());
        book.setReleaseDate(book.getReleaseDate().minusDays(50));
        book.setCreated(book.getCreated().plusDays(50));
        bookRepository.save(book);

        Book book1 = TestUtils.createBook(TestUtils.createAuthor());
        book1.setReleaseDate(LocalDate.now());
        bookRepository.save(book1);

        Book book2 = TestUtils.createBook(TestUtils.createAuthor());
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
        bookState.setLibranian(user);
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState);

        BookState bookState1 = TestUtils.createBookState(action, BookStateEnum.ZWROCONA);
        bookState1.setLibranian(user);
        bookState1.setBook(book);
        bookState1.setDateOfLoan(today.plusDays(10));
        bookState1.setDateOfReturn(today.plusDays(30));
        bookState1.setAction(action);
        bookState1.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState1);

        BookState bookState2 = TestUtils.createBookState(action, BookStateEnum.ZWROCONA);
        bookState2.setLibranian(user);
        bookState2.setBook(book);
        bookState2.setDateOfReturn(today.plusDays(40));
        bookState2.setDateOfLoan(today.plusDays(50));
        bookState2.setAction(action);
        bookState2.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState2);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        BookState bookState3 = TestUtils.createBookState(action4, BookStateEnum.WYPOZYCZONA);
        bookState3.setLibranian(user2);
        bookState3.setBook(book);
        bookState3.setAction(action4);
        bookState3.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState3);

        BookState bookState4 = TestUtils.createBookState(action4, BookStateEnum.WYPOZYCZONA);
        bookState4.setLibranian(user2);
        bookState4.setBook(book);
        bookState4.setDateOfLoan(today.plusDays(130));
        bookState4.setAction(action4);
        bookState4.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState4);

        BookState bookState5 = TestUtils.createBookState(action4, BookStateEnum.WYPOZYCZONA);
        bookState5.setLibranian(user2);
        bookState5.setBook(book);
        bookState5.setDateOfLoan(today.plusDays(50));
        bookState5.setAction(action4);
        bookState5.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
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

        assertEquals(Integer.valueOf(1500), bookRepository.sumPagesForUser(user2));
    }
}

