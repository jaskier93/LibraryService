package library.validators.mainValidators

import library.models.Payment
import library.models.User
import library.repositories.BookRepository
import library.repositories.PaymentRepository
import spock.lang.Specification
import spock.lang.Unroll

class PaymentAmountValidatorSpockTest extends Specification {
    PaymentRepository mockPaymentRepository = Mock(PaymentRepository)
    private User user = new User();

    PaymentAmountValidator validator = new PaymentAmountValidator(mockPaymentRepository)

    @Unroll
    def "PaymentAmountTest" () {
        setup:
        mockPaymentRepository.findPaymentsByUser(user) >> paymentList

        expect:
        expectedResult == validator.validator(user)

        where:
        paymentList                                                  || expectedResult
        [new Payment(), new Payment(), new Payment(), new Payment()] || true
        []                                                           || false
    }
}
