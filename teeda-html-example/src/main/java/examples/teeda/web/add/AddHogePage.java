/**
 *
 */
package examples.teeda.web.add;

/**
 * @author shot
 * 
 */
public class AddHogePage {

	private String a;

	private String b;

	private boolean show;

	public AddHogePage() {
		System.out.println("a");
	}

	public String doRead() {
		setShow(true);
		return null;
	}

	public String getA() {
		return a;
	}

	public String getB() {
		return b;
	}

	public boolean isShow() {
		return show;
	}

	public void setA(String a) {
		this.a = a;
	}

	public void setB(String b) {
		this.b = b;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
}
