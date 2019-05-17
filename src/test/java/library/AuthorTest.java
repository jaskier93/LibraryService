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

    //test passed! obiekty są prawidłowo usuwane z bazy po teście
    @Test
    public void authorTest() {
        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Author author2 = TestUtils.createAuthor();
        authorRepository.save(author2);

        assertNotEquals(author.getId(), author2.getId());
        assertEquals(author.getLastName(), author2.getLastName());
        assertFalse(authorRepository.findAuthorsByLastName("SapkowskiAndrzej").isEmpty());
        assertTrue(authorRepository.findAuthorsByLastName("fjkhsdf").isEmpty());

        authorRepository.delete(author);
        authorRepository.delete(author2);
    }
}
