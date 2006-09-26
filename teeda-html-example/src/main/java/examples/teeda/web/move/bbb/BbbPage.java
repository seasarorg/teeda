/**
 *
 */
package examples.teeda.web.move.bbb;

/**
 * @author shot
 */
public class BbbPage {

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
