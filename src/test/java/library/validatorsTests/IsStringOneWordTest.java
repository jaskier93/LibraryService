package library.validatorsTests;

import library.TestUtils;
import library.repositories.UserRepository;
import library.users.User;
import library.validators.IsStringOneWord;
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
public class IsStringOneWordTest {

    @Autowired
    private IsStringOneWord isStringOneWord;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private UserRepository userRepository;

    @After
    public void after() {
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    @Test //test passed! obiekty prawidłowo usuwane z bazy
    public void validatorTest() {

        User user = TestUtils.createUser();
        user.setName("Damian");
        userRepository.save(user);

        User user2 = TestUtils.createUser();
        user2.setName("Tomcio69KFC");
        userRepository.save(user2);

        User user3 = TestUtils.createUser();
        user3.setName("5746");
        userRepository.save(user3);

        User user4 = TestUtils.createUser();
        user4.setName("maciek");
        userRepository.save(user4);

        User user5 = TestUtils.createUser();
        user5.setName("Maciek       "); //teksty ze spacjami/znakami nie przejdą
        userRepository.save(user5);

        assertTrue(isStringOneWord.validator(user.getName()));
        assertFalse(isStringOneWord.validator(user2.getName()));
        assertFalse(isStringOneWord.validator(user3.getName()));
        assertFalse(isStringOneWord.validator(user4.getName()));
        assertFalse(isStringOneWord.validator(user5.getName()));
    }
}
