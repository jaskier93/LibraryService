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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookStateRepositoryTest {

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final BookStateRepository bookStateRepository = null;

    @Autowired
    private final ActionRepository actionRepository = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

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
    public void bookStateRepositoryTest() {

        LocalDate today = LocalDate.now();

        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);

        Book book2 = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book2);

        Book book3 = TestUtils.createBook(TestUtils.createAuthor());
        book3.setAgeCategory(AgeCategory.NASTOLATKOWIE);
        book3.setCategory(Category.SCIENCE);
        bookRepository.save(book3);

        User user = TestUtils.createUser();
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        userRepository.save(user2);

        User user3 = TestUtils.createUser();
        userRepository.save(user3);

        Action action = TestUtils.createAction(book, user);
        actionRepository.save(action);

        Action action4 = TestUtils.createAction(book, user2);
        actionRepository.save(action4);

        Action action5 = TestUtils.createAction(book, user3);
        actionRepository.save(action5);

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

        BookState bookState6 = TestUtils.createBookState(action5, BookStateEnum.ZWROCONA);
        bookState6.setLibranian(user2);
        bookState6.setBook(book2);
        bookState6.setAction(action5);
        bookState6.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState6);

        BookState bookState7 = TestUtils.createBookState(action5, BookStateEnum.NOWA);
        bookState7.setLibranian(user3);
        bookState7.setBook(book3);
        bookState7.setAction(action5);
        bookState7.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState7);

        //lista wypożyczeń ze zwróconymi książkami
        List<BookState> bookStateList = new ArrayList<>();
        bookStateList.add(bookState2);
        bookStateList.add(bookState1);
        bookStateList.add(bookState);

        //lista wypożyczeń wypożyczonymi książkami user2
        List<BookState> bookStateList2 = new ArrayList<>();
        bookStateList2.add(bookState4);
        bookStateList2.add(bookState5);
        bookStateList2.add(bookState3);

        List<Book> loanedBookList = new ArrayList<>();
        loanedBookList.add(book);

        BookState bookStateFromBase = bookStateRepository.getOne(bookState5.getId());
        assertEquals(bookState5.getId(), bookStateFromBase.getId());

        assertEquals(loanedBookList, bookStateRepository.findLoanedBooksByUser(user2));

        //  assertEquals(bookState2, bookStateRepository.findBookStateByBook(book.getId())); TODO: nie działa, prawdopodobnie metoda też nie jest poprawna

        assertEquals(bookStateList2, bookStateRepository.findCurrentBookStateByUser(user2));

        //test metody zwracającej listę wypożyczeń użytkownika uporzadkowaną według daty wypożyczenia (od najstarszej daty)
        assertEquals(bookStateList, bookStateRepository.findBookStateByUser(user));

        //DISTINCT wybiera tylko unikalne obiekty (książki)
        assertEquals(2, bookStateRepository.findBookByAgeCategory(AgeCategory.DOROSLI).size());

        assertEquals(2, bookStateRepository.findBookByCategory(Category.ADVENTURE).size());
        assertEquals(1, bookStateRepository.findBookByCategory(Category.SCIENCE).size());

        assertEquals(Integer.valueOf(1500), bookStateRepository.sumPagesForUser(user2));
    }
}
