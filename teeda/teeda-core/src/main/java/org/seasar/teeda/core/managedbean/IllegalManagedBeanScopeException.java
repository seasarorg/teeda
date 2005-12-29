package org.seasar.teeda.core.managedbean;

import org.seasar.teeda.core.exception.ExtendFacesException;

public class IllegalManagedBeanScopeException extends ExtendFacesException {

	private static final long serialVersionUID = 1L;

	public IllegalManagedBeanScopeException(String key, Object value){
		super("ETDA0009", new Object[]{key, value});
	}
}
