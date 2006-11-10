package org.seasar.teeda.it.web.validator;

public class ValidatorPage {

	public static final String aaa_TGreaterThanConstantValidator = "targetCommand='doValidate, doValidate2'";

	public static final String bbb_TGreaterValidator = "target='doValidate,doValidate2', targetId=aaa";

	public static final String bbb_TGreaterThanConstantValidator = "targetCommand='doValidate, doValidate2'";

	private Integer aaa;

	private Integer bbb;

	public Integer getBbb() {
		return bbb;
	}

	public void setBbb(Integer bbb) {
		this.bbb = bbb;
	}

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
