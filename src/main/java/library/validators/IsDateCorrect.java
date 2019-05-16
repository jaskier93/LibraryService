package library.validators;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class IsDateCorrect {
    public boolean validator(LocalDate localDate) { return localDate.isBefore(LocalDate.now());
    }
}
