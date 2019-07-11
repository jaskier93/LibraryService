package library.validators.mainValidators;

import library.models.User;
import org.springframework.stereotype.Component;

@Component //sprawdzić czy powinno być @Comp
public abstract class AbstractValidator {

    public abstract boolean validator(User user);

  //  public abstract ValidatorException createException();
}
