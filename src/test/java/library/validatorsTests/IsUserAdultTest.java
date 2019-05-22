package library.validatorsTests;

import library.TestUtils;
import library.repositories.UserRepository;
import library.users.User;
import library.validators.IsUserAdult;
import lombok.extern.slf4j.Slf4j;
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
public class IsUserAdultTest {

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final IsUserAdult isUserAdult = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    @Test
    public void validatorTest() {

        User user = TestUtils.createUser();
        user.setDateOfBirth(LocalDate.now().plusYears(17)); //w tym przypadku user ma ~3 lata

        User user2 = TestUtils.createUser();

        assertFalse(isUserAdult.validator(user));
        assertTrue(isUserAdult.validator(user2));
    }
}
