package examples.teeda.exception;

/**
 * @author yone
 * @author shot
 */
public class LoginAuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoginAuthException() {
	}

	public LoginAuthException(String message) {
		super(message);
	}

}