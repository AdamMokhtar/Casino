package casino.gamingmachine;
/**
 * the behaviour of this class does not have to be tested
 */
public class NoPlayerCardException extends Exception {
    public NoPlayerCardException() {
    }

    public NoPlayerCardException(String message) {
        super(message);
    }

    public NoPlayerCardException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPlayerCardException(Throwable cause) {
        super(cause);
    }

    public NoPlayerCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
