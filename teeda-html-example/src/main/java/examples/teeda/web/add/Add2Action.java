package examples.teeda.web.add;

public class Add2Action {

	private Add2Page add2Page;

	public Add2Page getAdd2Page() {
		return add2Page;
	}

	public void setAdd2Page(Add2Page add2Page) {
		this.add2Page = add2Page;
	}

	public String doCalculate() {
		int result = add2Page.getArg1() + add2Page.getArg2();
		add2Page.setResult(result);
		return null;
	}
}