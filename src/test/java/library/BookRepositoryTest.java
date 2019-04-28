package library;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Author;
import library.models.Book;
import library.repositories.AuthorRepository;
import library.repositories.BookRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookRepositoryTest {

    @Autowired
    private final BookRepository bookRepository = null;

    @Autowired
    private final AuthorRepository authorRepository = null;
    // BookState bookState;

    private Author author = new Author("Andrzej", "", "Sapkowski", LocalDate.of(2015, 12, 31),
            LocalDate.of(2015, 12, 31), LocalDate.of(2015, 12, 31), 5);


    @Before
    public void setUp(){
        authorRepository.save(author);
    }

    @After
    public void after(){
        authorRepository.delete(author);
    }

    @Test
    public void bookTest() {
        Book book = new Book("Wiedźmin", LocalDate.of(2015, 11, 22), LocalDate.of(2015, 12, 21),
                Category.ADVENTURE, AgeCategory.DOROŚLI, author, 5);
        bookRepository.save(book);

        Book book2 = new Book("Wiedźmin", LocalDate.of(2015, 11, 22), LocalDate.of(2015, 12, 21),
                Category.ADVENTURE, AgeCategory.DOROŚLI, author, 5);
        bookRepository.save(book2);


        assertNotNull(book.getId());
        assertNotEquals(book.getId(), book2.getId());

        Book bookFromBase = bookRepository.getOne(book2.getId());

        assertEquals(bookFromBase.getReleaseDate(), book2.getReleaseDate());

        bookRepository.delete(book);
        bookRepository.delete(book2);
    }
}