package examples.teeda.web.takeover;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yone
 */
public class TakeoverResultPage {

	private static final String TAKEOVER_HTML = "takeover";

	private Integer arg1;

	private Integer arg2;

	public Object arg3 = new Object();

	public transient List arg4 = new ArrayList();

	public static final String doInclude_TAKE_OVER = "type='include', properties='arg1,arg3,arg4'";

	public static final String doExclude_TAKE_OVER = "type='exclude', properties='arg1,arg3,arg4'";

	public static final String jumpTakeover_TAKE_OVER = "type=never";

	public void initialize() {
		arg4.add(new Object());
	}

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

	public String doInclude() {
		return TAKEOVER_HTML;
	}

	public String doExclude() {
		return TAKEOVER_HTML;
	}
}