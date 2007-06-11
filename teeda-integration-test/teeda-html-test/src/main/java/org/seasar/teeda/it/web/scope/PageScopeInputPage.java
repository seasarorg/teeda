package org.seasar.teeda.it.web.scope;

public class PageScopeInputPage {

	public static final String PAGE_SCOPE = "message";

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String doPageScopeExecute() {
		return "pageScopeResult";
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

}
