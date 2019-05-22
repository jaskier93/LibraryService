package library.validators;

import library.models.Book;
import library.repositories.BookStateRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class RentFifthBookValidator extends AbstractValidator {
    private final BookStateRepository bookStateRepository;

    @Autowired
    public RentFifthBookValidator(BookStateRepository bookStateRepository) {
        this.bookStateRepository = bookStateRepository;
    }

    /**
     * metoda metoda sprawdzająca, czy użytkownik może wypożyczyć piątą książkę
     * użytkownik musi być zarejestrowany przynajmniej rok
     * musi mieć aktualnie wypożyczone dokładnie 4 książki
     */
    @Override
    public boolean validator(User user) {
        List<Book> bookListLoanedByUser = bookStateRepository.findLoanedBooksByUser(user);
        Period period = Period.between(user.getDateOfRegistration(), LocalDate.now());
        return (bookListLoanedByUser.size() == 4
                && (period.getYears() >= 1));
    }
}
