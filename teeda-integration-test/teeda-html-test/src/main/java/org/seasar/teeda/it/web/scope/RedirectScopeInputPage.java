package org.seasar.teeda.it.web.scope;

public class RedirectScopeInputPage {

	public static final String REDIRECT_SCOPE = "message";

	private String message;

	private String hoge;

	public String getHoge() {
		return hoge;
	}

	public void setHoge(String hoge) {
		this.hoge = hoge;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Class doRedirectScopeExecute() {
		return RedirectScopeResult1Page.class;
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

}
