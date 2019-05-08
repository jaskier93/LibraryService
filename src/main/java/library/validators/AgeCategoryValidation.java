package library.validators;

import library.enums.AgeCategory;
import library.models.Book;

import library.users.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

@Component
@Data
public class AgeCategoryValidation {

    public boolean ageValidation(Book book, User user) {

        Long age = Duration.between(user.getDateOfBirth(), LocalDate.now()).toDays();

        //przeliczenie wieku na lata
        //nawet jeśli Integer utnie liczbę po przecinku, chodzi tylko sprawdzenie, czy wiek>18
        Integer years = age.intValue() / 365;

        if (book.getAgeCategory() == AgeCategory.DOROŚLI && years > 18) {
            System.out.println("Jesteś dorosły, możesz wypożyczyć dowolną książkę.");
            return true;
        }
        return false;
    }
}
