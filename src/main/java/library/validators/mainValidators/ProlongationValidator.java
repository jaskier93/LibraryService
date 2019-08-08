package library.validators.mainValidators;

import library.repositories.BookRepository;
import library.repositories.PaymentRepository;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * metoda ma sprawdzić, czy użytkownik spełnia warunki do przedłużenia wypożyczenia książki warunek będzie spełniony jeśli:
 * user nie ma żadnych płatności na koncie (nawet opłaconych)
 * ma wypożyczone góra 3 książki
 */
@Component
public class ProlongationValidator extends AbstractValidator {

    private final PaymentRepository paymentRepository;
    private final BookRepository bookRepository;
    private static final Integer MAX_AMOUNT_OF_BOOKS = 4;

    @Autowired
    public ProlongationValidator(PaymentRepository paymentRepository, BookRepository bookRepository) {
        this.paymentRepository = paymentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean validator(User user) {
        return (CollectionUtils.isEmpty(paymentRepository.findPaymentsByUser(user))
                && bookRepository.findLoanedBooksByUser(user).size() < MAX_AMOUNT_OF_BOOKS);
    }
}
