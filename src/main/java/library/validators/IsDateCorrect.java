package library.validators;

import java.time.LocalDate;


public class IsDateCorrect {
    public boolean validator(LocalDate localDate) {
        return localDate.isBefore(LocalDate.now());
    }
}
