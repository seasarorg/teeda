package examples.teeda.web.condition;

public class HiddenPage {

	private String hoge;

	public HiddenPage() {
		super();
	}

	public String getHoge() {
		return hoge;
	}

	public void setHoge(String hoge) {
		this.hoge = hoge;
	}

	public boolean isDisplay() {
		hoge.toString();
		return false;
	}

	public Class initialize() {
		hoge = "aaa";
		return null;
	}

	public Class prerender() {
		return null;
	}

}
