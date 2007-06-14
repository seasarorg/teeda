package examples.teeda.web.ajax;

public class LitboxviewPage {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String name) {
		this.message = name;
	}

	public String getLayout() {
		return null;
	}

	public Class doSave() {
		return LitboxPage.class;
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

}
