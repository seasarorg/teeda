package examples.teeda.web.dynamicproperty;

import java.util.Random;

public class DynamicpropertyPage {

	private static final String BLUE = "background-color:blue";

	private static final String YELLOW = "background-color:yellow";

	private static final String RED = "background-color:red";

	private static final Random random = new Random();

	private String aaa = "Dynamic";

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public String getAaaStyle() {
		int mod = Math.abs(random.nextInt()) % 3;
		switch (mod) {
		case 0:
			return BLUE;
		case 1:
			return YELLOW;
		default:
			return RED;
		}
	}
}
