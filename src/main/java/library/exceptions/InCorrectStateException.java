package library.exceptions;

public class InCorrectStateException extends RuntimeException {
    public InCorrectStateException(String message) {
        super(message);
    }
}
