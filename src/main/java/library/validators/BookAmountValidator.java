package library.validators;

import library.models.Book;
import library.repositories.BookStateRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookAmountValidator {

    private BookStateRepository bookStateRepository;

    @Autowired
    public BookAmountValidator(BookStateRepository bookStateRepository) {
        this.bookStateRepository = bookStateRepository;
    }

    //metoda sprawdzająca, czy dany użytkownik ma wypożyczone więcej niż 3 książki
    public boolean isMoreThanThree(User user, Book book) {

        if (bookStateRepository.findBooksByUser(user).size() > 3) {
            return true;
        }
        return false;
    }
}
