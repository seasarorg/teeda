package org.seasar.teeda.it.web.foreach;

public class ForeachConfirmPage {
	private String foo;

	private String bar;

	public String initialize() {
		System.out.println("<initialize>foo:" + foo);
		System.out.println("<initialize>bar:" + bar);
		return null;
	}

	public String prerender() {
		System.out.println("<prerender>foo:" + foo);
		System.out.println("<prerender>bar:" + bar);
		return null;
	}

	public Class doForeach() {
		System.out.println("<doForEach>foo:" + foo);
		System.out.println("<doForEach>bar:" + bar);
		return ForeachPage.class;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

}
