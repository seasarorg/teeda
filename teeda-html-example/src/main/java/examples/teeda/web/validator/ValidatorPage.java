package examples.teeda.web.validator;

public class ValidatorPage {

	public static final String aaa_TLengthValidator = "target='doValidate, doValidate2', minimum=3";

	private Integer aaa;

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

	public String doValidate() {
		return null;
	}

	public String doNoValidate() {
		return null;
	}

	public String doNoValidate2() {
		return null;
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

}
