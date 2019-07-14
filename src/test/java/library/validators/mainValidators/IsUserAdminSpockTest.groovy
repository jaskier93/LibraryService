package library.validators.mainValidators

import library.models.User
import library.repositories.UserRepository
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class IsUserAdminSpockTest extends Specification {

    UserRepository mockUserRepository = Mock(UserRepository)

    @Shared
    private User user = new User();
    @Shared
    private User user2 = new User();

    IsUserAdmin validator = new IsUserAdmin()

    @Unroll
    def "Expected result"() {

        setup:
        mockUserRepository.findAll() >> userList
        user.setAdmin(true)
        user2.setAdmin(false);
        expect:
        expectedResult == validator.validator(userList)

        where:
        userList || expectedResult
        user     || true
        user2    || false
    }
}
