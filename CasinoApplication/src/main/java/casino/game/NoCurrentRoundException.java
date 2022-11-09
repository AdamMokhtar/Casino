package casino.game;
/**
 * the behaviour of this class does not have to be tested
 */

public class NoCurrentRoundException extends Exception {
    public NoCurrentRoundException() {
    }

    public NoCurrentRoundException(String message) {
        super(message);
    }

    public NoCurrentRoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCurrentRoundException(Throwable cause) {
        super(cause);
    }

    public NoCurrentRoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
