package org.seasar.teeda.core.exception;

/**
 * @author shot
 *
 */
public class MethodNotAccessibleException extends ExtendMethodNotFoundExceptin {

	public MethodNotAccessibleException(Exception cause, String className, String expressionString){
		super(cause, className, expressionString, "ETDA0016");
	}
}
