/**
 *
 */
package examples.teeda.web.condition;

/**
 * @author shot
 * 
 */
public class CalculatePage {

	private Integer arg1;

	private Integer arg2;

	private boolean calType = false;

	private Integer result;

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

	public String doAdd() {
		System.out.println("doAdd");
		result = new Integer(arg1.intValue() + arg2.intValue());
		return null;
	}

	public String doMinus() {
		System.out.println("doMinus");
		result = new Integer(arg1.intValue() - arg2.intValue());
		return null;
	}

	public boolean isCalType() {
		return calType;
	}

	public void setCalType(boolean calType) {
		this.calType = calType;
	}

}
