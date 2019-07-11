package library.validators.dateValidators;

import library.TestUtils;
import library.repositories.UserRepository;
import library.models.User;
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
public class IsDateCorrectTest {

    @Autowired
    private final IsDateCorrect isDateCorrect = null;

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    @Test //test passed! obiekty są prawidłowo usuwane
    public void isDateNotFromFuture() {

        User user = TestUtils.createUser();
        user.setDateOfBirth(LocalDate.now().plusDays(1)); //jutrzejsza data-ma nie przechodzić
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        user2.setDateOfBirth(LocalDate.now()); //dzisiejsza data-ma przejść
        userRepository.save(user2);

        assertNotEquals(user.getDateOfBirth(), user2.getDateOfBirth());

        User userFromBase = userRepository.getOne(user.getId());

        assertFalse(isDateCorrect.validator(user.getDateOfBirth()));
        assertFalse(isDateCorrect.validator(userFromBase.getDateOfBirth()));
        assertTrue(isDateCorrect.validator(user2.getDateOfBirth()));
    }
}
