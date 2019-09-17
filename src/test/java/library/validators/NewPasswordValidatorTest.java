package library.validators;

import library.TestUtils;
import library.models.User;
import library.repositories.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NewPasswordValidatorTest {

    @Autowired
    private final NewPasswordValidator newPasswordValidator=null;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final UserRepository userRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    @Test
    public void isNewPasswordProper(){
        User user = TestUtils.createUser();
        user.setPassword("Dam14aaan"); //hasło spełnia wymogi
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        user2.setPassword("Damiaaaan"); //brak cyfry
        userRepository.save(user2);

        User user3 = TestUtils.createUser();
        user3.setName("5746535445"); //brak wielkiej litery na początku
        userRepository.save(user3);

        User user4 = TestUtils.createUser();
        user4.setName("dam14aaaan"); //brak wielkiej litery na początku
        userRepository.save(user4);

        User user5 = TestUtils.createUser();
        user5.setName("Dam14an"); //hasło za krótkie
        userRepository.save(user5);


        assertTrue(newPasswordValidator.validator(user.getPassword()));
        assertFalse(newPasswordValidator.validator(user2.getPassword()));
        assertFalse(newPasswordValidator.validator(user3.getPassword()));
        assertFalse(newPasswordValidator.validator(user4.getPassword()));
        assertFalse(newPasswordValidator.validator(user5.getPassword()));
    }
}