package org.seasar.teeda.it.web.error;

public class ErrorOnInitializePage {

	public String initialize() {
		throw new WebAppRuntimeException("ErrorPage throws exception.");
	}
}
