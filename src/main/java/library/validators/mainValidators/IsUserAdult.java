package library.validators.mainValidators;


import library.exceptions.InCorrectStateException;
import library.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class IsUserAdult extends AbstractValidator {

    /**
     * metoda obliczająca wiek użytkownika, potrzebna będzie do weryfikacji, czy może wypożyczyć daną książkę dla dorosłych
     * kontrolerze gdy otrzymamy info o książce i userze, wystarczy najpierw dać warunek sprawdzający, czy
     * Age.Category==DOROSLI, jeśli tak, wstawić ten walidator
     */
    @Override
    public boolean validator(User user) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(user.getDateOfBirth(), today);
        return (period.getYears() >= 18);
    }

    @Override
    public RuntimeException validatorException() {
        return new InCorrectStateException("Użytkownik nie jest dorosły");
    }
}
