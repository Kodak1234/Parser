package parser;


public class IllegalOperatorException extends RuntimeException {
    public IllegalOperatorException() {
        super();
    }

    public IllegalOperatorException(String message) {
        super(message);
    }

    public IllegalOperatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalOperatorException(Throwable cause) {
        super(cause);
    }

    protected IllegalOperatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
