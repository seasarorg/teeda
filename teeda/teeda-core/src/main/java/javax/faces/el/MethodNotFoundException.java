package javax.faces.el;

public class MethodNotFoundException extends EvaluationException {

    public MethodNotFoundException() {
        super();
    }

    public MethodNotFoundException(String message) {
        super(message);
    }

    public MethodNotFoundException(Throwable cause) {
        super(cause);
    }

    public MethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
