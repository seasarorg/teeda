/**
 *
 */
package org.seasar.teeda.it.web.transition.from;

/**
 * @author shot
 */
public class ToPage {

	private String previousViewId;

	public String prerender() {
		System.out.println(previousViewId);
		return null;
	}
	public String getPreviousViewId() {
		return previousViewId;
	}

	public void setPreviousViewId(String previousViewId) {
		this.previousViewId = previousViewId;
	}
	
	
}
