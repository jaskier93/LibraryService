package library.validators.mainValidators;

import library.exceptions.InCorrectStateException;
import library.models.Book;
import library.repositories.BookRepository;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class RentFifthBookValidator extends AbstractValidator {

    private final BookRepository bookRepository;

    @Autowired
    public RentFifthBookValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * metoda metoda sprawdzająca, czy użytkownik może wypożyczyć piątą książkę
     * użytkownik musi być zarejestrowany przynajmniej rok
     * musi mieć aktualnie wypożyczone dokładnie 4 książki
     */
    @Override
    public boolean validator(User user) {
        List<Book> bookListLoanedByUser = bookRepository.findLoanedBooksByUser(user);
        Period period = Period.between(user.getCreated().toLocalDate(), LocalDate.now());
        return (bookListLoanedByUser.size() == 4
                && (period.getYears() >= 1));
    }

    @Override
    public RuntimeException validatorException() {
        return new InCorrectStateException("Użytkownik nie jest zarejestrowany wystarczająo długo lub posiada zbyt dużo wypożyczonych książek na koncie.");
    }

}
