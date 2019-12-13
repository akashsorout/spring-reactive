package in.co.bytehub.learn.reactive.exception;

public class RecordNotFoundException extends BaseException {

    private static String defaultMsg = "Record not Found";

    public RecordNotFoundException() {
        super(defaultMsg);
    }

    public RecordNotFoundException(String msg) {
        super(msg);
    }
}
