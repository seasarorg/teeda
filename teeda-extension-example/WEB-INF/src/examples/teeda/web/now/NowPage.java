package examples.teeda.web.now;

import java.util.Date;

public class NowPage {

	private Date now;

	public Date getNow() {
		return now;
	}

	public String initialize() {
		now = new Date();
		return null;
	}
}
