package in.co.bytehub.learn.reactive.exception;

public abstract class BaseException extends RuntimeException {

    private static String msg = "Some Error Occurred";

    public BaseException() {
        super(msg);
    }

    public BaseException(String message) {
        super(message);
    }
}
