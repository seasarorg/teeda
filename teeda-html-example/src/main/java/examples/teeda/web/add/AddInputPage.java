package examples.teeda.web.add;

public class AddInputPage {

	public static final String arg1_TRequiredValidator = null;

	public static final String arg1_lengthValidator = "minimum=3";

	public static final String arg2_TRequiredValidator = null;

	public static final String arg2_lengthValidator = "minimum=3";

	private Integer arg1;

	private Integer arg2;

	private Integer result;

	public String doCalculate() {
		result = new Integer(arg1.intValue() + arg2.intValue());
		return "addResult";
	}

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

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

}