package examples.teeda.web.foreach.test;

import java.io.Serializable;

/**
 * @author yone
 */
public class HogeItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String foo;
	
	private BarItem[] bbbItems;

	public HogeItem() {
	}

	public BarItem[] getBbbItems() {
		return bbbItems;
	}

	public void setBbbItems(BarItem[] barItems) {
		this.bbbItems = barItems;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}
}
