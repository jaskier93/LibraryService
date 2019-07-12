package library.validators.mainValidators

import library.models.Payment
import library.models.User
import library.repositories.PaymentRepository
import spock.lang.Specification
import spock.lang.Unroll

class PaymentSumValidatorSpockTest extends Specification {
    PaymentRepository mockPaymentRepository = Mock(PaymentRepository)
    private User user = new User();

    PaymentSumValidator validator = new PaymentSumValidator(mockPaymentRepository)

    @Unroll
    def "PaymentSumTest"() {
        setup:
        mockPaymentRepository.sumPaymentsForOneUser(user) >> paymentList

        expect:
        expectedResult == validator.validator(user)

/*

        where:
        paymentList                                                                                          || expectedResult
       [new Payment(amount: 35), new Payment(amount: 50), new Payment(amount: 25), new Payment(amount: 45)] || true
       [new Payment(amount: 150)]                                                                           || true
        []                                                                                                   || false
        [new Payment(amount: 50)]

*/

        where:
        paymentList                                                  || expectedResult
        [new Payment(), new Payment(), new Payment(), new Payment()] || true
        []                                                           || false

    }
}
