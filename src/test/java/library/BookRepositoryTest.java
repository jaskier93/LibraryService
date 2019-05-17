package library;

import library.enums.AgeCategory;
import library.enums.BookStateEnum;
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


import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookRepositoryTest {

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;


    @After
    public void after() {
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
    }

    //test passed!
    @Test
    public void bookTest() {
        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);

        assertNotNull(book.getId());
        Book bookFromBase = bookRepository.getOne(book.getId());
        assertTrue(bookRepository.findBookByAgeCategory(AgeCategory.NASTOLATKOWIE).isEmpty());
        assertFalse(book.getTitle().isEmpty());
        assertEquals(book.getId(), bookFromBase.getId());
        assertEquals(bookFromBase.getReleaseDate(), book.getReleaseDate());
    }
}