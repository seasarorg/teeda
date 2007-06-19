package examples.teeda.web.initializetran;

public class Initializetran2Page {

	private String aaa;

	/**
	 * @return the aaa
	 */
	public String getAaa() {
		return aaa;
	}

	/**
	 * @param aaa
	 *            the aaa to set
	 */
	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public Class initialize() {
		return Initializetran3Page.class;
	}
}