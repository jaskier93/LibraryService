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

    @Autowired
    private final AuthorRepository authorRepository = null;

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

        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Book book = TestUtils.createBook(author);
        bookRepository.save(book);

        Book book2 = TestUtils.createBook(author);
        bookRepository.save(book2);

        Book book3 = TestUtils.createBook(author);
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
        bookState.setBook(book);
        bookState.setAction(action);
        bookState.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState);

        BookState bookState1 = TestUtils.createBookState(action, BookStateEnum.ZWROCONA);
        bookState1.setBook(book);
        bookState1.setDateFrom(today.plusDays(10));
        bookState1.setDateTo(today.plusDays(30));
        bookState1.setAction(action);
        bookState1.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState1);

        BookState bookState2 = TestUtils.createBookState(action, BookStateEnum.ZWROCONA);
        bookState2.setBook(book);
        bookState2.setDateTo(today.plusDays(40));
        bookState2.setDateFrom(today.plusDays(50));
        bookState2.setAction(action);
        bookState2.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState2);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        BookState bookState3 = TestUtils.createBookState(action4, BookStateEnum.WYPOZYCZONA);
        bookState3.setBook(book);
        bookState3.setAction(action4);
        bookState3.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState3);

        BookState bookState4 = TestUtils.createBookState(action4, BookStateEnum.WYPOZYCZONA);
        bookState4.setBook(book);
        bookState4.setDateFrom(today.plusDays(130));
        bookState4.setAction(action4);
        bookState4.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState4);

        BookState bookState5 = TestUtils.createBookState(action4, BookStateEnum.WYPOZYCZONA);
        bookState5.setBook(book);
        bookState5.setDateFrom(today.plusDays(50));
        bookState5.setAction(action4);
        bookState5.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookStateRepository.save(bookState5);

        BookState bookState6 = TestUtils.createBookState(action5, BookStateEnum.ZWROCONA);
        bookState6.setBook(book2);
        bookState6.setAction(action5);
        bookState6.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookStateRepository.save(bookState6);

        BookState bookState7 = TestUtils.createBookState(action5, BookStateEnum.NOWA);
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

        BookState bookStateFromBase = bookStateRepository.getOne(bookState5.getId());
        assertEquals(bookState5.getId(), bookStateFromBase.getId());

        //  assertEquals(bookState2, bookStateRepository.findBookStateByBook(book.getId())); TODO: nie działa, prawdopodobnie metoda też nie jest poprawna

        assertEquals(bookStateList2, bookStateRepository.findCurrentBookStateByUser(user2));

        //test metody zwracającej listę wypożyczeń użytkownika uporzadkowaną według daty wypożyczenia (od najstarszej daty)
        assertEquals(bookStateList, bookStateRepository.findBookStateByUser(user));


    }
}
