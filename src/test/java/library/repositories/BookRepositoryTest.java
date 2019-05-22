package library.repositories;

import library.TestUtils;
import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Book;
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
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
    }

    //test passed!
    @Test
    public void querryTest() {
        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);

        Book book1 = bookRepository.getOne(book.getId());
        assertEquals(book.getId(), book1.getId());

        assertFalse(bookRepository.findBookByTitle("WiedźminWiedźmin").isEmpty());
        assertEquals("WiedźminWiedźmin", book.getTitle());
        assertNotEquals("sdfssdffds", book.getTitle());
        assertEquals(book.getAgeCategory(), AgeCategory.DOROSLI);
        assertNotEquals(book.getAgeCategory(), AgeCategory.NAJMLODSI);
        assertEquals(book.getCategory(), Category.ADVENTURE);
        assertNotEquals(book.getCategory(), Category.DICTIONARY);
        assertFalse(bookRepository.findBookByAgeCategory(AgeCategory.DOROSLI).isEmpty());
        assertTrue(bookRepository.findBookByAgeCategory(AgeCategory.NAJMLODSI).isEmpty());
        assertFalse(bookRepository.findBookByCategory(Category.ADVENTURE).isEmpty());
        assertTrue(bookRepository.findBookByCategory(Category.THRILLER).isEmpty());
    }
}
