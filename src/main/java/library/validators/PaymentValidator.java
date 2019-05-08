package library.validators;

import library.models.Payment;
import library.repositories.PaymentRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentValidator {

    public static final Integer MAX_PAYMENTS_AMOUNT = 3;

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentValidator(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * metoda zwraca booleana w zależności od tego czy użytkownik ma już maksymalną ilość płatności
     */
    public Boolean hasMoreThan3Payments(User user) {
        List<Payment> paymentList = paymentRepository.findByUser(user);
        if (paymentList.size() >= MAX_PAYMENTS_AMOUNT) {
            return true;
        }
        return false;
    }
}
