package library;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Author;
import library.models.Book;
import library.models.BookState;
import library.repositories.BookRepository;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookTest {

    @Autowired
    private BookRepository bookRepository;
    BookState bookState;

    Author author = new Author("Andrzej", "", "Sapkowski", LocalDate.of(2015, 12, 31),
            LocalDate.of(2015, 12, 31), LocalDate.of(2015, 12, 31), 5);

    @Test
    public void bookTest() {
        Book book = new Book("Wiedźmin", LocalDate.of(2015, 12, 31), LocalDate.of(2015, 12, 31),
                Category.ADVENTURE, AgeCategory.DOROŚLI, bookState, author, 5);
        bookRepository.save(book);

        Book book2 = new Book("Wiedźmin", LocalDate.of(2015, 12, 31), LocalDate.of(2015, 12, 31),
                Category.ADVENTURE, AgeCategory.DOROŚLI, bookState, author, 5);
        bookRepository.save(book2);

        assertEquals(book.getId(), book2.getId());

    }
}
