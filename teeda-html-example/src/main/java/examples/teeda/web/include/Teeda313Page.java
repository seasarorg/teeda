package examples.teeda.web.include;

import examples.teeda.web.add.AddPage;

public class Teeda313Page {

	private int result;

	private AddPage addPage;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Class doAction() {
		System.out.println("doAction");
		result = addPage.getArg1() + addPage.getArg2();
		return null;
	}

	public Class initialize() {
		return null;
	}

	public Class prerender() {
		return null;
	}

	/**
	 * @return the addPage
	 */
	public AddPage getAddPage() {
		return addPage;
	}

	/**
	 * @param addPage
	 *            the addPage to set
	 */
	public void setAddPage(AddPage addPage) {
		this.addPage = addPage;
	}

}
