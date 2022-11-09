package casino.cashier;
/**
 * the behaviour of this class does not have to be tested
 */

public class BetNotExceptedException extends Exception {
    public BetNotExceptedException() {
    }

    public BetNotExceptedException(String message) {
        super(message);
    }

    public BetNotExceptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BetNotExceptedException(Throwable cause) {
        super(cause);
    }

    public BetNotExceptedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
