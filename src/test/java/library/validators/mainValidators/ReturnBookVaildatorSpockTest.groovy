package library.validators.mainValidators

import library.models.BookState
import library.models.User
import library.repositories.BookStateRepository
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class ReturnBookVaildatorSpockTest extends Specification {

    BookStateRepository mockBookStateRepository = Mock(BookStateRepository)
    @Shared
    private User user = new User();
    @Shared
    private BookState bookState = new BookState();
    @Shared
    private BookState bookState2 = new BookState();
    @Shared
    private BookState bookState3 = new BookState();

    ReturnBookVaildator validator = new ReturnBookVaildator(mockBookStateRepository)

    @Unroll
    def "Expected result"() {
        LocalDate date = LocalDate.now().plusDays(15);
        LocalDate date2 = LocalDate.now();
        LocalDate date3 = LocalDate.now().minusDays(15);

        setup:
        bookState.setDateOfReturn(date);
        mockBookStateRepository.save(bookState);

        bookState2.setDateOfReturn(date2);
        mockBookStateRepository.save(bookState2);

        bookState3.setDateOfReturn(date3);
        mockBookStateRepository.save(bookState3);

        mockBookStateRepository.findCurrentBookStateByUser(user) >> bookStateList;

        expect:
        expectedResult == validator.validator(user)

        where:
        bookStateList || expectedResult
        [bookState]   || false
        [bookState2]  || true
        [bookState3]  || true

    }
}
