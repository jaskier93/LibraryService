package library.repositories;

import library.TestUtils;
import library.users.User;
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
public class UserRepositoryTest {

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final JdbcTemplate jdbcTemplate = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    //test passed!
    @Test
    public void userRepositoryTest() {
        LocalDate today = LocalDate.now();

        User user = TestUtils.createUser();
        userRepository.save(user);

        User user1 = TestUtils.createUser();
        userRepository.save(user1);

        User user2 = TestUtils.createUser();
        user2.setDateOfBirth(today.minusYears(16));
        userRepository.save(user2);

        User userFromBase = userRepository.getOne(user.getId());

        assertEquals(user.getId(), userFromBase.getId());
        assertNotEquals(user2.getId(), userFromBase.getId());
        assertFalse(userRepository.findUserByLastName("XXXYYYZZZ").isEmpty());
        assertTrue(userRepository.findUserByLastName("sdfsdfsdffsd").isEmpty());
        //   assertEquals(2, userRepository.findAdultUsers(today.minusYears(18)).size()); //libranian i user1 są dorośli
        assertEquals(user, userRepository.findUserById(user.getId()));
    }
}
