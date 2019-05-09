package library.validators;

import library.enums.AgeCategory;
import library.models.Book;
import library.users.User;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

@Component
public class UserAgeValidation extends AbstractValidator {

/*    public boolean ageValidation(Book book, User user) {
      Long age = Duration.between(user.getDateOfBirth(), LocalDate.now()).toDays();
 */
    /**przeliczenie wieku na lata*/
    /*
        Integer years = age.intValue() / 365;
        return (book.getAgeCategory() == AgeCategory.DOROŚLI && years > 18);
    }*/

    @Override
    public boolean validator(User user) {
        Long age = Duration.between(user.getDateOfBirth(), LocalDate.now()).toDays();
        Integer years = age.intValue() / 365;

        return (years > 18);
    }


}
