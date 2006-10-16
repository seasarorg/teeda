package examples.teeda.web.add;

import java.math.BigDecimal;

public class AddExtPage {

	public static final String arg1_numberConverter = "pattern='#.0000', type='currency'";

	public static final String arg1_TNumberLengthValidator = "integralMax=12,fractionMax=2";

	public static final String arg2_numberConverter = "pattern='#.00', type='currency'";

	public static final String result_numberConverter = "pattern='#.0000', type='currency'";

	private BigDecimal arg1;

	private BigDecimal arg2;

	private BigDecimal result;

	private int arg1Fraction = 4;

	private int arg2Fraction = 2;

	public int getArg1Fraction() {
		return arg1Fraction;
	}

	public int getArg2Fraction() {
		return arg2Fraction;
	}

	public void setArg1Fraction(int arg1Fraction) {
		this.arg1Fraction = arg1Fraction;
	}

	public void setArg2Fraction(int arg2Fraction) {
		this.arg2Fraction = arg2Fraction;
	}

	public BigDecimal getArg1() {
		return arg1;
	}

	public void setArg1(BigDecimal arg1) {
		this.arg1 = arg1;
	}

	public BigDecimal getArg2() {
		return arg2;
	}

	public void setArg2(BigDecimal arg2) {
		this.arg2 = arg2;
	}

	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}

	public String doCalculate() {
		if (arg1 == null) {
			arg1 = new BigDecimal("0");
		}
		if (arg2 == null) {
			arg2 = new BigDecimal("0");
		}
		result = arg1.add(arg2);
		return null;
	}
}