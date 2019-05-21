package library.validatorsTests;

import library.TestUtils;
import library.repositories.UserRepository;
import library.users.User;
import library.validators.IsUserAdmin;
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
public class IsUsedAdminTest {

    @Autowired
    private IsUserAdmin isUserAdmin;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    @Test //test passed! obiekty prawidłowo usuwane z bazy
    public void validatorTest() {

        User user = TestUtils.createUser();
        user.setAdmin(true);
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        userRepository.save(user2);

        assertTrue(isUserAdmin.validator(user));
        assertFalse(isUserAdmin.validator(user2));
    }
}
