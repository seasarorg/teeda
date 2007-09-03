package examples.teeda.web.include;

public class AddPage {

	private int arg1;

	private int arg2;

	public int getArg1() {
		return arg1;
	}

	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}

	public int getArg2() {
		return arg2;
	}

	public void setArg2(int arg2) {
		this.arg2 = arg2;
	}

	public Class initialize() {
		System.out.println("add init");
		return null;
	}

	public Class prerender() {
		System.out.println("add prerender");
		return null;
	}

}
