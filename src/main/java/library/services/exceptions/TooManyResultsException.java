package library.services.exceptions;

public class TooManyResultsException extends RuntimeException {
    public TooManyResultsException(String message) {
        super(message);
    }
}
