package library.validators.dateValidators;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class IsDateCorrect {
    /**
     * metoda do sprawdzania poprawnośći wprowadzanej daty, np daty rejestracji, narodzin, śmierci itd
     * localDate = localDate.plusDays(1) spowoduje, że możliwe będzie wpisanie dzisiejszej daty
     */
    public boolean validator(LocalDate localDate) {
        localDate = localDate.minusDays(1);
        return localDate.isBefore(LocalDate.now());
    }
}
