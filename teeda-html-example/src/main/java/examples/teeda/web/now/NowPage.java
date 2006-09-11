package examples.teeda.web.now;

import java.util.Date;

public class NowPage {

	public static final String now_TDateTimeConverter = "pattern='yy/MM/dd', threshold=69";

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

}
