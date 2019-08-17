package library.security;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class CreateUserCommand {
    @NotNull
    @Length(min = 6, max = 20)
    private String login;

    @NotNull
    @Length(min = 6, max = 20)
    private String password;
}
