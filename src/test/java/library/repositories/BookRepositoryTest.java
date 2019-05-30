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

    @After
    public void after() {
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
        jdbcTemplate.update("delete from books where title='WiedźminWiedźmin'");
    }

    //test passed!
    @Test
    public void bookRepositoryTest() {

        LocalDate today = LocalDate.now();
        LocalDateTime today2 = LocalDateTime.now();

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

        assertFalse(bookRepository.findBookByTitle("WiedźminWiedźmin").isEmpty());
        assertEquals("WiedźminWiedźmin", book2.getTitle());
        assertNotEquals("sdfssdffds", book2.getTitle());
        assertEquals(book2.getAgeCategory(), AgeCategory.DOROSLI);
        assertNotEquals(book2.getAgeCategory(), AgeCategory.NAJMLODSI);
        assertEquals(book2.getCategory(), Category.ADVENTURE);
        assertNotEquals(book2.getCategory(), Category.DICTIONARY);
        assertFalse(bookRepository.findBookByAgeCategory(AgeCategory.DOROSLI).isEmpty());
        assertTrue(bookRepository.findBookByAgeCategory(AgeCategory.NAJMLODSI).isEmpty());
        assertFalse(bookRepository.findBookByCategory(Category.ADVENTURE).isEmpty());
        assertTrue(bookRepository.findBookByCategory(Category.THRILLER).isEmpty());

        //test ma sprawdzić, czy w przeciągu określonego czasu, zostały dodane jakieś książki do biblioteki
        assertFalse(bookRepository.booksAddedInPeriod(today.minusDays(111)).isEmpty());
        assertTrue(bookRepository.booksAddedInPeriod(today.plusDays(111)).isEmpty());

        //test ma sprawdzić, czy w  bibliotece są ksiązki wydane w przeciągu określonego czasu
        assertFalse(bookRepository.booksReleasedInPeriod(today2.minusYears(10)).isEmpty());
        assertTrue(bookRepository.booksReleasedInPeriod(today2.plusYears(10)).isEmpty());

        //test ma na celu sprawdzić, czy książki są posortowane prawidłowo po dacie wydania (od najstarszej)
        assertEquals(bookListOrderByReleaseDate, bookRepository.sortedBooksByReleaseData());

        //test ma na celu sprawdzić, czy książki są posortowane prawidłowo po dacie dodania do biblioteki (od najstarszej)
        assertEquals(bookListOrderedByAddingDate, bookRepository.sortedBooksByAddingData());
    }
}
