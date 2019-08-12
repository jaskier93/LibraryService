package library.validators.mainValidators;

import library.exceptions.ExceptionEmptyList;
import library.exceptions.InCorrectStateException;
import library.models.Payment;
import library.repositories.PaymentRepository;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void validatorException(User user) {
        Integer amount = paymentRepository.sumPaymentsForOneUser(user);
        if (amount < MAX_PAYMENTS_SUM || amount == null) {
            throw new ExceptionEmptyList("Użytkownik nie przekroczył dozwolonej sumy płatności.");
        }
    }
}
