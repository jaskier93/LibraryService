package library.validators.mainValidators;

import library.repositories.PaymentRepository;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentSumValidator extends AbstractValidator {

    private static final Integer MAX_PAYMENTS_SUM = 100;

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentSumValidator(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * metoda zwraca informacje czy łączna suma płatności użytkownika przekracza maksymalną dozwoloną sumę płatności
     */
    @Override
    public boolean validator(User user) {
        Integer amount = paymentRepository.sumPaymentsForOneUser(user);
        return amount >= MAX_PAYMENTS_SUM;
    }
}
