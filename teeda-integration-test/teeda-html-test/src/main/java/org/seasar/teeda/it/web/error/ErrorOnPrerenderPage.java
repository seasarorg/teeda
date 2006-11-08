package org.seasar.teeda.it.web.error;

public class ErrorOnPrerenderPage {

	public String prerender() {
		throw new WebAppRuntimeException("ErrorPage throws exception.");
	}
}
