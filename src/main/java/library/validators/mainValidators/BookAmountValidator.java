package library.validators.mainValidators;

import library.models.Book;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookAmountValidator extends AbstractValidator {

    private final BookRepository bookRepository;

    @Autowired
    public BookAmountValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * metoda sprawdzająca, czy dany użytkownik może wypożyczyć czwartą książkę
     * jeśli ma wypożyczone już więcej niż 3 książki-metoda zwróci false
     */
    @Override
    public boolean validator(User user) {
        List<Book> bookList = bookRepository.findLoanedBooksByUser(user);
        return bookList.size() <= 3;
    }

}
