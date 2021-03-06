package library.validators.mainValidators;

import library.TestUtils;
import library.models.User;
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
public class IsUserAdminTest {

    @Autowired
    private final IsUserAdmin isUserAdmin = null;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    @Test //test passed! obiekty prawidłowo usuwane z bazy
    public void isUserAdmin() {

        User user = TestUtils.createUser();
        user.setAdmin(true);

        User user2 = TestUtils.createUser();
        // domyślnie libranian.getAdmin =false

        assertTrue(isUserAdmin.validator(user));
        assertFalse(isUserAdmin.validator(user2));
    }
}
