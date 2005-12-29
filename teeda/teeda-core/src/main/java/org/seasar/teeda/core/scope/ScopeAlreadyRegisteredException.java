package org.seasar.teeda.core.scope;

import org.seasar.teeda.core.exception.ExtendFacesException;

public class ScopeAlreadyRegisteredException extends ExtendFacesException {

	private static final long serialVersionUID = 1L;

	public ScopeAlreadyRegisteredException(Object[] args){
		super("ETDA0010", args);
	}
}
