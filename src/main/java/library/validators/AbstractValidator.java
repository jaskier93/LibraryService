package library.validators;

import library.models.Book;
import library.users.User;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractValidator {

    public abstract boolean validator(User user);
}
