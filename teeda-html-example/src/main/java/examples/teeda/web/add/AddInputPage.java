package examples.teeda.web.add;

public class AddInputPage {

	public static final String arg1_TRequiredValidator = null;

	public static final String arg1_lengthValidator = "minimum=3";

	public static final String arg2_TRequiredValidator = null;

	public static final String arg2_lengthValidator = "minimum=3";

	public static final String arg1_integerConverter = null;

	private Integer arg1;

	private Integer arg2;

	public Integer getArg1() {
		return arg1;
	}

	public void setArg1(Integer arg1) {
		this.arg1 = arg1;
	}

	public Integer getArg2() {
		return arg2;
	}

	public void setArg2(Integer arg2) {
		this.arg2 = arg2;
	}

	public String initialize() {
		return null;
	}
}