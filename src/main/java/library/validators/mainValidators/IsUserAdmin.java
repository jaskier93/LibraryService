package library.validators.mainValidators;

import library.exceptions.InCorrectStateException;
import library.exceptions.TooManyResultsException;
import library.models.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IsUserAdmin extends AbstractValidator {
    //metoda zwraca informację, czy user ma status admina
    @Override
    public boolean validator(User user) {
        return user.isAdmin();
    }

    @Override
    public void validatorException(User user) {
        if (!user.isAdmin())
            throw new InCorrectStateException("Użytkownik nie posiada uprawnień administratora");
    }
}
