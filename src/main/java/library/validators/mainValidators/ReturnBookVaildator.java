package library.validators.mainValidators;

import library.exceptions.InCorrectStateException;
import library.models.BookState;
import library.repositories.BookStateRepository;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReturnBookVaildator extends AbstractValidator {

    private final BookStateRepository bookStateRepository;

    @Autowired
    public ReturnBookVaildator(BookStateRepository bookStateRepository) {
        this.bookStateRepository = bookStateRepository;
    }

    @Override
    public boolean validator(User user) {
        List<BookState> bookStateList = bookStateRepository.findCurrentBookStateByUser(user);
        int counter = 0;
        for (int i = 0; i < bookStateList.size(); i++) {
            //warunek sprawdza, czy data zwrotu jest przed/taka sama jak data dzisiejsza (dla każdej aktualnie wypożyczonej książki użytkownika)
            if ((LocalDate.now().plusDays(1).isAfter(bookStateList.get(i).getDateFrom().plusDays(30))))
                counter++;
        }
        return bookStateList.size() == counter;
    }

    @Override
    public RuntimeException validatorException() {
        return new InCorrectStateException("Użytkownik przetrzymuje książkę zbyt długo!");
    }
}
