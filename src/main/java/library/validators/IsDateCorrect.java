package library.validators;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class IsDateCorrect {
    /**
     *metoda do sprawdzania poprawnośći wprowadzanej daty, np daty rejestracji, narodzin, śmierci itd
     */
    public boolean validator(LocalDate localDate) { return localDate.isBefore(LocalDate.now());
    }
}
