package library;

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
    private final BookRepository bookRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from books");
        jdbcTemplate.update("delete from author");
    }

    //test passed! obiekty są prawidłowo usuwane z bazy po teście
    @Test
    public void querryTest() {
        Book book = TestUtils.createBook();
        bookRepository.save(book);

        Book book1 = bookRepository.getOne(book.getId());
        assertEquals(book.getId(), book1.getId());

        assertFalse(bookRepository.findBookByTitle("Wiedźmin").isEmpty());
        assertEquals("Wiedźmin", book.getTitle());
        assertNotEquals("sdfssdffds", book.getTitle());
    }
}
