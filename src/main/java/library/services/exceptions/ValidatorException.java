package library.services.exceptions;

public class ValidatorException extends RuntimeException{

    public ValidatorException(String message) {
        super(message);
    }
}