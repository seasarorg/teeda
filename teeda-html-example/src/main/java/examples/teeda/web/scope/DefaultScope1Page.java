package examples.teeda.web.scope;

public class DefaultScope1Page {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Class doDefaultScope2() {
		return DefaultScope2Page.class;
	}

}
