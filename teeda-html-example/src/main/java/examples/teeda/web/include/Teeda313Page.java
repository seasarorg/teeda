package examples.teeda.web.include;

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
		addPage.setArg2(12345);
		return null;
	}

	public Class initialize() {
		System.out.println("teeda313 init");
		return null;
	}

	public Class prerender() {
		System.out.println("teeda313 prerender");
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
