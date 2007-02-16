package examples.teeda.web.include;

public class Include2Page {

	private String aaa;

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public String initialize() {
		System.out.println(getClass() + " initialized");
		return null;
	}

	public String prerender() {
		System.out.println(getClass() + " prerendered");
		return null;
	}

}
