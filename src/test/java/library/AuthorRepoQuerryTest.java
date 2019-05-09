package library;

import library.models.Author;
import library.repositories.AuthorRepository;
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

    @Test
    private void querryTest() {
        Author author = TestUtils.createAuthor();
        authorRepository.save(author);

        Author author2 = authorRepository.getOne(author.getId());
        authorRepository.save(author2);

        assertEquals(author.getId(), author2.getId());
        assertEquals(true, !authorRepository.findAuthorByLastName("Sapkowski").isEmpty());

        authorRepository.delete(author);
    }
}
