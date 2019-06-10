package library.validators.mainValidators;

import library.repositories.BookStateRepository;
import library.repositories.PaymentRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * metoda ma sprawdzić, czy użytkownik spełnia warunki do przedłużenia wypożyczenia książki warunek będzie spełniony jeśli:
 * libranian nie ma żadnych płatności na koncie (nawet opłaconych)
 * ma wypożyczone góra 3 książki
 * TODO: ewentualnie do zmiany: płatności tylko aktywne
 */
@Component
public class ProlongationValidator extends AbstractValidator {
    private final PaymentRepository paymentRepository;
    private final BookStateRepository bookStateRepository;

    private static final Integer MAX_AMOUNT_OF_BOOKS = 4;

    @Autowired
    public ProlongationValidator(PaymentRepository paymentRepository, BookStateRepository bookStateRepository) {
        this.paymentRepository = paymentRepository;
        this.bookStateRepository = bookStateRepository;
    }

    @Override
    public boolean validator(User user) {
        return (paymentRepository.findPaymentsByUser(user).isEmpty()
                && bookStateRepository.findLoanedBooksByUser(user).size() < MAX_AMOUNT_OF_BOOKS);
    }
}
