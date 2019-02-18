package Exception;

public class UndefinedVariableException extends RuntimeException {
    public UndefinedVariableException() {
        super("Variable is not defined");
    }
}
