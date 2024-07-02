package utilities.exceptions;

public class NotValidKeyException extends RuntimeException {
    public NotValidKeyException(int keycode) {
        super("Key '" + (char)keycode + "' is invalid");
    }
}
