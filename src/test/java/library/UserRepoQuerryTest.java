package library;

import library.repositories.UserRepository;
import library.users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepoQuerryTest {

    @Autowired
    private final UserRepository userRepository = null;

    //test passed!
    @Test
    public void querryTest() {
        User user = TestUtils.createUser();
        userRepository.save(user);

        User user1 = userRepository.getOne(user.getId());

        assertNotNull(user.getId());
        assertEquals(user.getId(), user1.getId());
        assertEquals(user.getDateOfBirth(), user1.getDateOfBirth());
        assertFalse(userRepository.findUserByLastName("XXXYYYZZZ").isEmpty());
        assertTrue(userRepository.findUserByLastName("sdfsdfsdffsd").isEmpty());

        userRepository.delete(user);
    }
}
