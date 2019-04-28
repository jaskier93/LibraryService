package library.users;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class User extends Person {

    public User() {
        super(false);
    }
}
