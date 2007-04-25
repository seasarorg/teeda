package examples.teeda.web.add;

import java.util.Map;

public class MultiplePage {

	private Integer arg1;

	private Map requestScope;

	public Map getRequestScope() {
		return requestScope;
	}

	public void setRequestScope(Map requestScope) {
		this.requestScope = requestScope;
	}

	public Integer getArg1() {
		return arg1;
	}

	public void setArg1(Integer arg1) {
		this.arg1 = arg1;
	}

	public String doCalculate() {
		arg1 = new Integer(arg1.intValue() * 2);
		System.out.println(requestScope);
		return null;
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

}
