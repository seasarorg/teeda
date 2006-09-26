/**
 *
 */
package examples.teeda.web.move.aaa;

/**
 * @author shot
 */
public class Aaa2Page {

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
