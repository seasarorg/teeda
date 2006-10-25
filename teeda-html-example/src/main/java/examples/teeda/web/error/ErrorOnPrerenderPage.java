package examples.teeda.web.error;

public class ErrorOnPrerenderPage {

	public String prerender() {
		throw new WebAppRuntimeException("ErrorPage throws exception.");
	}
}
