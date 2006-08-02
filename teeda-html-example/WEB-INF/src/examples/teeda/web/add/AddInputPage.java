package examples.teeda.web.add;

public class AddInputPage {

	public static final String arg1_VALIDATOR = "#{'validator':'length','minimum':3}";

	public static final String arg2_VALIDATOR = "#{'validator':'length','minimum':3}";

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
}