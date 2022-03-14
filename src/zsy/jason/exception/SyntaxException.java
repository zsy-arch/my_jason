package zsy.jason.exception;

public class SyntaxException extends Exception {
    public SyntaxException() {
        super("Syntax error!");
    }

    public SyntaxException(String message) {
        super("Syntax error: " + message);
    }
}
