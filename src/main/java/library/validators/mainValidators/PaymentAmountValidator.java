package library.validators.mainValidators;

import library.exceptions.ValidatorException;
import library.models.Payment;
import library.repositories.PaymentRepository;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentAmountValidator extends AbstractValidator {

    private static final Integer MAX_PAYMENTS_AMOUNT = 3;

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentAmountValidator(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /*
    metoda zwraca booleana w zależności od tego czy użytkownik ma już maksymalną ilość płatności
    */
    @Override
    public boolean validator(User user) {
        List<Payment> paymentList = paymentRepository.findPaymentsByUser(user);
        return (paymentList.size() >= MAX_PAYMENTS_AMOUNT);
    }

    public RuntimeException validatorException() {
        return new ValidatorException("Użytkownik nie posiada płatności na koncie lub nie przekroczył dozwolonej ilości płatności na koncie.");
    }
}
