package library.validators.mainValidators;

import library.models.Book;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.services.exceptions.ValidatorException;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * metoda sprawdzająca, czy dany użytkownik może wypożyczyć czwartą książkę
 * jeśli ma wypożyczone już więcej niż 3 książki-metoda zwróci false
 */
@Component
public class BookAmountValidator extends AbstractValidator {

    private final BookRepository bookRepository;

    @Autowired
    public BookAmountValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public boolean validator(User user) {
        List<Book> bookList = bookRepository.findLoanedBooksByUser(user);
        return bookList.size() <= 3;
    }

    @Override
    public ValidatorException createException() {
        return new ValidatorException("Użytkownik posiada 3 książki wypożyczone - oddaj przynajmniej jedną aby wypożyczyć drugą");
    }
}
