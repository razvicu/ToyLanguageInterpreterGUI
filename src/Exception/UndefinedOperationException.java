package Exception;

public class UndefinedOperationException extends RuntimeException {
    public UndefinedOperationException() {
        super("Operation not defined");
    }
}
