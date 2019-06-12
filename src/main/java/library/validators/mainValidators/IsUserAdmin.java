package library.validators.mainValidators;

import library.users.User;
import org.springframework.stereotype.Component;

@Component
public class IsUserAdmin extends AbstractValidator {
    //metoda zwraca informację, czy user ma status admina
    @Override
    public boolean validator(User user) {
        return user.isAdmin();
    }
}
