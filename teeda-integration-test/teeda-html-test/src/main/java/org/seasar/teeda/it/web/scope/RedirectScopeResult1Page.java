package org.seasar.teeda.it.web.scope;

public class RedirectScopeResult1Page {

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

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

}
