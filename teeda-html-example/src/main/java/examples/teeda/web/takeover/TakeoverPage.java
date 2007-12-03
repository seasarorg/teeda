package examples.teeda.web.takeover;

import java.util.List;

/**
 * @author yone
 */
public class TakeoverPage {

	private Integer arg1;

	private Integer arg2;

	public Object arg3;

	public transient List arg4;

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

	public String doNothing() {
		return "takeoverResult";
	}
}