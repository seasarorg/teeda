package examples.teeda.web.foreach;

import java.io.Serializable;

public class MyDto implements Serializable {

	private String aaa;

	private String bbb;

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	/**
	 * @return the bbb
	 */
	public String getBbb() {
		return bbb;
	}

	/**
	 * @param bbb the bbb to set
	 */
	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

}
