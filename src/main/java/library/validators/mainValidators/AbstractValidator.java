package library.validators.mainValidators;

import library.models.User;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractValidator {

    public abstract boolean validator(User user);
    public abstract void validatorException(User user);
}
