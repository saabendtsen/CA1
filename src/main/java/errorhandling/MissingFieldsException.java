package errorhandling;

public class MissingFieldsException extends Exception {
    public MissingFieldsException(String message) {
        super("Field input Error: "+message);
    }
}