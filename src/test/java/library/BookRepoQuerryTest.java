package library;

import library.models.Author;
import library.models.Book;
import library.repositories.AuthorRepository;
import library.repositories.BookRepository;
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

    @Autowired
    private final AuthorRepository authorRepository = null;

    //test passed
    @Test
    public void querryTest() {
        Author author = TestUtils.createAuthor();
        authorRepository.save(author);
        Book book = TestUtils.createBook();
        book.setTitle("Wiedźmin");
        book.setAuthor(author);
        bookRepository.save(book);

        assertFalse(bookRepository.findBookByTitle("Wiedźmin").isEmpty());
        assertEquals("Wiedźmin", book.getTitle());

        bookRepository.delete(book);
        authorRepository.delete(author);
    }
}
