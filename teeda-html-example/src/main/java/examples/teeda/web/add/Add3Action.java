/**
 *
 */
package examples.teeda.web.add;

/**
 * @author shot
 *
 */
public class Add3Action {

	private Add3Page add3Page;

	private Add3Service add3Service;

	public Add3Service getAdd3Service() {
		return add3Service;
	}

	public void setAdd3Service(Add3Service add3Service) {
		this.add3Service = add3Service;
	}

	public Add3Page getAdd3Page() {
		return add3Page;
	}

	public void setAdd3Page(Add3Page add3Page) {
		this.add3Page = add3Page;
	}

	public String doCalculate() {
		add3Page = add3Service.doCalculate(this.add3Page);
		return null;
	}

}
