package examples.teeda.web.add;

public class AddResultDoublePage {

	private Double arg1;

	private Double arg2;

	private Double result;

	public Double getArg1() {
		return arg1;
	}

	public void setArg1(Double arg1) {
		this.arg1 = arg1;
	}

	public Double getArg2() {
		return arg2;
	}

	public void setArg2(Double arg2) {
		this.arg2 = arg2;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public String initialize() {
		result = new Double(arg1.doubleValue() + arg2.doubleValue());
		return null;
	}
}