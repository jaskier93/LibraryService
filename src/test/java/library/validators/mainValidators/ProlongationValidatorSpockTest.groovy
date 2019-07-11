package library.validators.mainValidators

import library.models.Book
import library.models.Payment
import library.repositories.BookRepository
import library.repositories.PaymentRepository
import library.models.User
import spock.lang.Specification
import spock.lang.Unroll

class ProlongationValidatorSpockTest extends Specification {

    PaymentRepository mockPaymentRepository = Mock(PaymentRepository)
    BookRepository mockBookRepository = Mock(BookRepository)
    private User user = new User();

    ProlongationValidator validator = new ProlongationValidator(mockPaymentRepository, mockBookRepository)

    @Unroll
    def "Expected result: #expectedResult and booklist size: #bookList.size"() {
        setup:
        mockPaymentRepository.findPaymentsByUser(user) >> paymentList

        and:
        mockBookRepository.findLoanedBooksByUser(user) >> bookList

        expect:
        expectedResult == validator.validator(user)

        where:
        paymentList   | bookList                                                         || expectedResult
        [new Payment()] | [new Book()]                                                   || false
        [] | [new Book(),new Book(),new Book()]                                          || true

    }
}
