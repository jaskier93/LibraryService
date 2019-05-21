package library.validators;

import library.models.Book;
import library.repositories.BookStateRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookAmountValidator extends AbstractValidator {

    private BookStateRepository bookStateRepository;

    @Autowired
    public BookAmountValidator(BookStateRepository bookStateRepository) {
        this.bookStateRepository = bookStateRepository;
    }

    /**
     * metoda sprawdzająca, czy dany użytkownik ma wypożyczone więcej niż 3 książki
     */
    @Override
    public boolean validator(User user) {
        List<Book> bookList = bookStateRepository.findLoanedBooksByUser(user);
        return (bookList.size() > 3 && (!bookList.isEmpty()));
    }

}
