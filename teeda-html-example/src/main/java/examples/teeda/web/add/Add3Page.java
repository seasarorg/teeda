/**
 *
 */
package examples.teeda.web.add;

/**
 * @author shot
 * 
 */
public class Add3Page {

	public static final String arg1_lengthValidator = "minimum=3";

	public static final String arg2_lengthValidator = "minimum=3";

	private int arg1;

	private int arg2;

	private int result;

	private String bbb;

	private Integer aaa;

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

	public String initialize() {
		aaa = new Integer(100);
		bbb = "This is Page + Action + ActionSupportInterceptor example.";
		result = 1000;
		return null;
	}

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
