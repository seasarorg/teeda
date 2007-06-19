package examples.teeda.web.captcha;

import examples.teeda.web.hello.HelloPage;

public class CaptchaPage {

	public static final String captcha_TRequiredValidator = null;

	public static final String captcha_captchaValidator = null;

	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Class doFinish() {
		return HelloPage.class;
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

}
