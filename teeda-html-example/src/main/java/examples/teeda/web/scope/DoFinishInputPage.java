package examples.teeda.web.scope;

public class DoFinishInputPage {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String doFinishExecute() {
		return "doFinishResult";
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

}
