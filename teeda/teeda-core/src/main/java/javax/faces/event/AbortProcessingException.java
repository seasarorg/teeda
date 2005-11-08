package javax.faces.event;

import javax.faces.FacesException;


public class AbortProcessingException extends FacesException {

    public AbortProcessingException() {
        super();
    }

    public AbortProcessingException(String message) {
        super(message);
    }

    public AbortProcessingException(Throwable cause) {
        super(cause);
    }

    public AbortProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

}
