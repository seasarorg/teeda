package examples.teeda.web.error;


public class ErrorPage {

	public String doHoge() {
		throw new WebAppRuntimeException();
	}
}
