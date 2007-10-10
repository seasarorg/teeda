package org.seasar.teeda.it.web.foreach;

import java.io.Serializable;

public class NoItemsSavePage {

	private String aaa;
	private String bbb;
	private String ccc;

	private Foo[] fooItems;
	private int fooIndex;

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

	public String getCcc() {
		return ccc;
	}

	public void setCcc(String ccc) {
		this.ccc = ccc;
	}

	public Foo[] getFooItems() {
		return fooItems;
	}

	public void setFooItems(Foo[] fooItems) {
		this.fooItems = fooItems;
	}

	public int getFooIndex() {
		return fooIndex;
	}

	public void setFooIndex(int fooIndex) {
		this.fooIndex = fooIndex;
	}

	public String prerender() {

		if (fooItems == null) {
			fooItems = new Foo[] { createItem("a1", "b1", "c1"),
					createItem("a2", "b2", "c2"), createItem("a3", "b3", "c3") };
		}
		return null;
	}

	public String doTest() {
		return null;
	}

	private Foo createItem(String aaa, String bbb, String ccc) {
		Foo item = new Foo();
		item.setAaa(aaa);
		item.setBbb(bbb);
		item.setCcc(ccc);
		return item;
	}

	public static class Foo implements Serializable {

		private static final long serialVersionUID = 1L;

		private String aaa;
		private String bbb;
		private String ccc;

		public String getAaa() {
			return aaa;
		}

		public void setAaa(String aaa) {
			this.aaa = aaa;
		}

		public String getBbb() {
			return bbb;
		}

		public void setBbb(String bbb) {
			this.bbb = bbb;
		}

		public String getCcc() {
			return ccc;
		}

		public void setCcc(String ccc) {
			this.ccc = ccc;
		}
	}

}
