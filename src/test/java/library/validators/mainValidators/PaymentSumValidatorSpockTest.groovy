package library.validators.mainValidators

import library.models.User
import library.repositories.PaymentRepository
import spock.lang.Specification
import spock.lang.Unroll

class PaymentSumValidatorSpockTest extends Specification {
    PaymentRepository mockPaymentRepository = Mock(PaymentRepository)
    private User user = new User();

    PaymentSumValidator validator = new PaymentSumValidator(mockPaymentRepository)

    @Unroll
    def "PaymentSumTest #liczba"() {
        setup:
        mockPaymentRepository.sumPaymentsForOneUser(user) >> paymentSum

        expect:
        expectedResult == validator.validator(user)


        where:
        paymentSum || expectedResult
        1          || false
        99         || false
        100        || true
        350        || true
    }
}
