package library.exceptions;

public class LoginExistsException extends RuntimeException {
    public LoginExistsException(String login) {
        super("Istnieje już konto o podanym loginie:  " + login);
    }

}
