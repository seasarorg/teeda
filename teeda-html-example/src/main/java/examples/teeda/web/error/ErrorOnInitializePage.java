package examples.teeda.web.error;

public class ErrorOnInitializePage {

	public String initialize() {
		throw new WebAppRuntimeException("ErrorPage throws exception.");
	}
}
