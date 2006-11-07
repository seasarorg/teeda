package org.seasar.teeda.it.web.error;

public class ErrorPage {

	public String doHoge() {
		throw new WebAppRuntimeException("ErrorPage throws exception.");
	}
}
