
package library.validators;

import library.TestUtils;
import library.models.Author;
import library.repositories.AuthorRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IsDeathDateCorrectTest {

    @Autowired
    private final IsDeathDateCorrect isDeathDateCorrect = null;

    @Autowired
    private final AuthorRepository authorRepository = null;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from author where last_name='SapkowskiAndrzej'");
    }

    @Test //test passed! obiekty są prawidłowo usuwane
    public void isDeathDateNotFromFutureAndAfterBirthDay() {
        Author author = TestUtils.createAuthor();
        author.setDateOfDeath(LocalDate.now());
        authorRepository.save(author);

        Author author2 = TestUtils.createAuthor();
        author2.setDateOfDeath(LocalDate.now().plusDays(1));
        authorRepository.save(author2);

        Author author3 = TestUtils.createAuthor();
        author3.setDateOfDeath(author3.getDateOfBirth().minusMonths(1));
        authorRepository.save(author3);

        Author author4 = TestUtils.createAuthor();
        author4.setDateOfDeath(author4.getDateOfBirth().plusYears(15));
        authorRepository.save(author4);

        assertTrue(isDeathDateCorrect.validator(author));
        assertTrue(isDeathDateCorrect.validator(author4));
        assertFalse(isDeathDateCorrect.validator(author2));
        assertFalse(isDeathDateCorrect.validator(author3));
    }
}
