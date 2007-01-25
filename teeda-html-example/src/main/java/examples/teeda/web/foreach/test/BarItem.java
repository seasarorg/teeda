package examples.teeda.web.foreach.test;

import java.io.Serializable;

/**
 * @author yone
 */
public class BarItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String bar;

	public BarItem() {
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

}
