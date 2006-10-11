package examples.teeda.web.now;

import java.util.Date;

public class Now2Page {

	public static final String now_TRequiredValidator = null;

	public static final String now_dateTimeConverter = "pattern='yyyy/MM'";

	private String nowPattern = "yyyy/MM";

	private Date now;

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public String initialize() {
		return null;
	}

	public String doCalculate() {
		return null;
	}

	public String getNowPattern() {
		return nowPattern;
	}

	public void setNowPattern(String nowPattern) {
		this.nowPattern = nowPattern;
	}

}
