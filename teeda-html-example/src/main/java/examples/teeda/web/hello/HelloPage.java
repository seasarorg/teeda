package examples.teeda.web.hello;

public class HelloPage {

	private String name = "Seasar2";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String initialize() {
		System.out.println("initialize");
		return null;
	}

	public String prerender() {
		System.out.println("prerender");
		return null;
	}
}