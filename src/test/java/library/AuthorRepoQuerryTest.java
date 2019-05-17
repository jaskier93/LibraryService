package library;

import library.models.Author;
import library.repositories.AuthorRepository;
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
public class AuthorRepoQuerryTest {

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("delete a from author a where a.lastName='SapkowskiAndrzej'"); //poprawić
        jdbcTemplate.update("delete a from author"); //działa
    }

    //test passed!
    @Test
    public void querryTest() {
        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Author author2 = authorRepository.getOne(author.getId());

        assertEquals(author.getId(), author2.getId());
        assertFalse(authorRepository.findAuthorsByLastName("SapkowskiAndrzej").isEmpty());
        assertTrue(authorRepository.findAuthorsByLastName("fssfdsddfs").isEmpty());
    }
}
