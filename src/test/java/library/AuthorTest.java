package library;

import library.models.Author;
import library.repositories.AuthorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorTest {

    @Autowired
    private final AuthorRepository authorRepository = null;

    @Test
    public void authorTest() {

      //  LocalDate x= LocalDate.of(LocalDate);

        Author author = new Author("Andrzej", "", "Sapkowski",
                LocalDate.of(2015, 12, 31),
                LocalDate.of(2015, 12, 31),
                LocalDate.of(2015, 12, 31), 5);
        authorRepository.save(author);

        Author author2 = new Author("Andrzej", "", "Sapkowski",
                LocalDate.of(2015, 12, 31),
                LocalDate.of(2015, 12, 31),
                LocalDate.of(2015, 12, 31), 5);
        authorRepository.save(author2);

        assertNotEquals(author.getId(), author2.getId());  //test przeszedł
        assertEquals(author.getLastName(), author2.getLastName()); //test przeszedł

        authorRepository.delete(author);
        authorRepository.delete(author2);

    }
}
