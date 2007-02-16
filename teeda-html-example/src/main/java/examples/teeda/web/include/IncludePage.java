package examples.teeda.web.include;

public class IncludePage {

	private String result;

	private Include2Page include2Page;

	private Include3Page include3Page;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String doAction() {
		result = include2Page.getAaa() + include3Page.getAaa();
		return null;
	}

	public String initialize() {
		System.out.println(getClass() + " initialized");
		return null;
	}

	public String prerender() {
		System.out.println(getClass() + " prerendered");
		return null;
	}

	/**
	 * @return the include2Page
	 */
	public Include2Page getInclude2Page() {
		return include2Page;
	}

	/**
	 * @param include2Page
	 *            the include2Page to set
	 */
	public void setInclude2Page(Include2Page include2Page) {
		this.include2Page = include2Page;
	}

	/**
	 * @return the include3Page
	 */
	public Include3Page getInclude3Page() {
		return include3Page;
	}

	/**
	 * @param include3Page
	 *            the include3Page to set
	 */
	public void setInclude3Page(Include3Page include3Page) {
		this.include3Page = include3Page;
	}
}
