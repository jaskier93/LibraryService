package library.validators.mainValidators;

import library.services.exceptions.ValidatorException;
import library.users.User;
import org.springframework.stereotype.Component;

@Component //sprawdzić czy powinno być @Comp
public abstract class AbstractValidator {

    public abstract boolean validator(User user);

    public abstract ValidatorException createException();
}
