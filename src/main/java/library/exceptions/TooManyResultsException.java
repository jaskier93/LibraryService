package library.exceptions;

public class TooManyResultsException extends RuntimeException {
    public TooManyResultsException(String message) {
        super(message);
    }
}
