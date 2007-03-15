package org.seasar.teeda.it.web.reload;

public class RegistPage extends AbstractRegistPage {
	public Class doRegist() {
		return RegistConfirmPage.class;
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}
}