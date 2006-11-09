/**
 *
 */
package examples.teeda.web.condition;

/**
 * @author shot
 */
public class Condition2Page {

	public static final String hoge_lengthValidator = "minimum=3";

	private int hoge;

	private Boolean aaa = null;

	private Boolean bbb = null;

	public int getHoge() {
		return hoge;
	}

	public void setHoge(int hoge) {
		this.hoge = hoge;
	}

	public String initialize() {
		aaa = Boolean.TRUE;
		return null;
	}

	public void setAaa(Boolean aaa) {
		this.aaa = aaa;
	}

	public Boolean isAaa() {
		return aaa;
	}

	public Boolean isBbb() {
		return bbb;
	}

	public void setBbb(Boolean bbb) {
		this.bbb = bbb;
	}

	public String doHoge() {
		return null;
	}
}
