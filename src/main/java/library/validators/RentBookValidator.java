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
public class RentBookValidator {
    private BookStateRepository bookStateRepository;
    private ActionRepository actionRepository;

    @Autowired
    public RentBookValidator(BookStateRepository bookStateRepository, ActionRepository actionRepository) {
        this.bookStateRepository = bookStateRepository;
        this.actionRepository = actionRepository;
    }

    public boolean canRentFifthBook(User user) {
        List<Book> bookList = bookStateRepository.findBooksByUser(user);

        if (bookList.size() >= 4 && (
                Duration.between(user.getDateOfRegistration(), LocalDate.now()).toDays() > 365)) {
            return true;
        }
        return false;
    }

    /**
     * metoda sprawdzająca, czy użytkownik ma zniszczenia książki na koncie
     * w przypadku, gdyby metoda miała sprawdzać, czy zdarzyło się to np w ostatnim miesiącu-należy dodać pole z datą akcji w klasie Action
     * zamiast tego można sprawdzić ile zniszczeń ma użytkownik, jeśli ma np 10-może dostać bana, czyli user.setActive(false)-konto zbanowane
     */
    public boolean isDestroyer(User user) {
        /**
         * List<Action> actionList = actionRepository.findActionWithDestroyedBooksByUser(user);
         *
         jeśli jest  przynajmniej jedno zniszczenie, zwraca true i np można wymagać od usera zapłacenia kaucji zwracanej
         po oddaniu książki niezniszczonej-po weryfikacji bibliotekarza*/
        if (actionRepository.findActionWithDestroyedBooksByUser(user).size() > 0) {
            return true;
        }
        return false;
    }
}
