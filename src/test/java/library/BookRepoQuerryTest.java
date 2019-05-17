package library;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Author;
import library.models.Book;
import library.repositories.AuthorRepository;
import library.repositories.BookRepository;
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
public class BookRepoQuerryTest {

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @Autowired
    private final BookRepository bookRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from books");
        jdbcTemplate.update("delete from author");
    }

    //test passed! sql do poprawy
    @Test
    public void querryTest() {
        Book book = TestUtils.createBook(TestUtils.createAuthor());
        bookRepository.save(book);

        Book book1 = bookRepository.getOne(book.getId());
        assertEquals(book.getId(), book1.getId());

        assertFalse(bookRepository.findBookByTitle("WiedźminWiedźmin").isEmpty());
        assertEquals("WiedźminWiedźmin", book.getTitle());
        assertNotEquals("sdfssdffds", book.getTitle());
        assertEquals(book.getAgeCategory(), AgeCategory.DOROŚLI);
        assertNotEquals(book.getAgeCategory(), AgeCategory.NAJMŁODSI);
        assertEquals(book.getCategory(), Category.ADVENTURE);
        assertNotEquals(book.getCategory(), Category.DICTIONARY);
        assertFalse(bookRepository.findBookByAgeCategory(AgeCategory.DOROŚLI).isEmpty());
        assertTrue(bookRepository.findBookByAgeCategory(AgeCategory.NAJMŁODSI).isEmpty());
        assertFalse(bookRepository.findBookByCategory(Category.ADVENTURE).isEmpty());
        assertTrue(bookRepository.findBookByCategory(Category.THRILLER).isEmpty());
    }
}
