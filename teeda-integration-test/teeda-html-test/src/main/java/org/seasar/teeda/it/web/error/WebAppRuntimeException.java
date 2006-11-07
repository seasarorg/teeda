/**
 *
 */
package org.seasar.teeda.it.web.error;

/**
 * @author shot
 */
public class WebAppRuntimeException extends RuntimeException {

	public WebAppRuntimeException() {
	}
	
	public WebAppRuntimeException(String message) {
		super(message);
	}
}
