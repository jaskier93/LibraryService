package library.validators;

import library.enums.AgeCategory;
import library.models.Book;
import library.users.User;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

@Component
public class UserAgeValidation extends AbstractValidator {

    /*metoda obliczająca wiek użytkownika, potrzebna będzie do weryfikacji, czy może wypożyczyć daną książkę dla dorosłych*/
    @Override
    public boolean validator(User user) {
        long age = Duration.between(user.getDateOfBirth(), LocalDate.now()).toDays();
        float years = age / 365.25f;
        return (years > 18);
    }
}