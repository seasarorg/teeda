/**
 *
 */
package examples.teeda.web.condition;

/**
 * @author shot
 */
public class ConditionInputPage {

	private Boolean aaa = Boolean.TRUE;

	private Boolean bbb = null;

	public String initialize() {
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


}
