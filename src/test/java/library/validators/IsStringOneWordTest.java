package library.validators;

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

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IsStringOneWordTest {

    @Autowired
    private final IsStringOneWord isStringOneWord = null;

    @Autowired
    public final JdbcTemplate jdbcTemplate = null;

    @Autowired
    private final UserRepository userRepository = null;

    @After
    public void after() {
        jdbcTemplate.update("delete from user where last_name='XXXYYYZZZ'");
    }

    @Test //test passed! obiekty prawidłowo usuwane z bazy
    public void isStringOneWordAndStartsWithUpperCase() {

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


        User user6 = TestUtils.createUser();
        user6.setSecondName(null);
        userRepository.save(user6);



        assertTrue(isStringOneWord.validator(user.getName()));
        assertFalse(isStringOneWord.validator(user2.getName()));
        assertFalse(isStringOneWord.validator(user3.getName()));
        assertFalse(isStringOneWord.validator(user4.getName()));
        assertFalse(isStringOneWord.validator(user5.getName()));
        assertFalse(isStringOneWord.validator(""));
        assertFalse(isStringOneWord.validator(user6.getSecondName()));
    }
}
