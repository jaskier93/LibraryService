package library.validators.mainValidators;

import library.exceptions.TooManyResultsException;
import library.models.Book;
import library.repositories.BookRepository;
import library.models.User;
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
    public RuntimeException validatorException() {
        return new TooManyResultsException("Użytkownik posiada 3 książki wypożyczone - oddaj przynajmniej jedną aby wypożyczyć następną");
    }
}
