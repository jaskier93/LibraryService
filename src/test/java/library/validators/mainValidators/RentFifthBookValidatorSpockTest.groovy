package library.validators.mainValidators

import library.TestUtils
import library.models.Book
import library.models.Payment
import library.models.User
import library.repositories.BookRepository
import library.repositories.PaymentRepository
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class RentFifthBookValidatorSpockTest extends Specification {

    BookRepository mockBookRepository = Mock(BookRepository)
    private User user = new User();

    RentFifthBookValidator validator = new RentFifthBookValidator(mockBookRepository)

    @Unroll
    def "Expected result: #bookList.size"() {
        setup:
        user.created = LocalDateTime.now().minusYears(2);
        mockBookRepository.findLoanedBooksByUser(user) >> bookList

        expect:
        expectedResult == validator.validator(user)

        where:
        bookList                                                                 || expectedResult
        [new Book()]                                                             || false
        [new Book(), new Book(), new Book(), new Book(), new Book(), new Book()] || false
        [new Book(), new Book(), new Book(), new Book()]                         || true

    }
}
