package library.validators;

import library.models.Action;
import library.models.Book;
import library.repositories.ActionRepository;
import library.repositories.BookStateRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Component
public class RentFifthBookValidator extends AbstractValidator {
    private BookStateRepository bookStateRepository;

    @Autowired
    public RentFifthBookValidator(BookStateRepository bookStateRepository) {
        this.bookStateRepository = bookStateRepository;
    }

/*    public boolean canRentFifthBook(User user) {
        List<Book> bookList = bookStateRepository.findBooksByUser(user);
        Long daysSinceRegistration = Duration.between(user.getDateOfRegistration(), LocalDate.now()).toDays();
        return (bookList.size() >= 4 && (daysSinceRegistration > 365));
    }*/

    @Override
    public boolean validator(User user) {
        List<Book> bookList = bookStateRepository.findBooksByUser(user);
        Long daysSinceRegistration = Duration.between(user.getDateOfRegistration(), LocalDate.now()).toDays();
        return (bookList.size() >= 4 && (daysSinceRegistration > 365));
    }
}
