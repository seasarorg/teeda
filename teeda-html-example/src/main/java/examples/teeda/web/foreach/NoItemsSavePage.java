package examples.teeda.web.foreach;

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
			fooItems = new Foo[7];
			fooItems[0] = createItem("a1", "b1", "c1");
			fooItems[1] = createItem("a2", "b2", "c2");
			fooItems[2] = createItem("a3", "b3", "c3");
			fooItems[3] = createItem("a4", "b4", "c4");
			fooItems[4] = createItem("a5", "b5", "c5");
			fooItems[5] = createItem("a6", "b6", "c6");
			fooItems[6] = createItem("a7", "b7", "c7");
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
