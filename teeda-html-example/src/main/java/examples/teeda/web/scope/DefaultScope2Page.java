package examples.teeda.web.scope;

public class DefaultScope2Page {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Class doDefaultScope3() {
		return DefaultScope3Page.class;
	}

}
