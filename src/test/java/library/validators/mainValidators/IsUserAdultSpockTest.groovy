package library.validators.mainValidators

import library.models.User
import library.repositories.UserRepository
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class IsUserAdultSpockTest extends Specification {

    UserRepository mockUserRepository = Mock(UserRepository)
    @Shared
    private User user = new User();
    @Shared
    private User user2 = new User();

    IsUserAdult validator = new IsUserAdult()

    @Unroll
    def "Expected result"() {

        setup:
        mockUserRepository.findAll() >> userList
        user.setDateOfBirth(LocalDate.now().minusYears(25));
        user2.setDateOfBirth(LocalDate.now().minusYears(12));

        expect:
        expectedResult == validator.validator(userList)

        where:
        userList || expectedResult
        user     || true
        user2    || false
    }
}
