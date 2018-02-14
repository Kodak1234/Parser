package parser;


public class IllegalExpression extends RuntimeException {
    public IllegalExpression() {
    }

    public IllegalExpression(String message) {
        super(message);
    }

    public IllegalExpression(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalExpression(Throwable cause) {
        super(cause);
    }

    public IllegalExpression(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
