package javax.faces.el;

import javax.faces.FacesException;

public class EvaluationException extends FacesException {

	public EvaluationException() {
		super();
	}

	public EvaluationException(String message) {
		super(message);
	}

	public EvaluationException(Throwable cause) {
		super(cause);
	}

	public EvaluationException(String message, Throwable cause) {
		super(message, cause);
	}

}
