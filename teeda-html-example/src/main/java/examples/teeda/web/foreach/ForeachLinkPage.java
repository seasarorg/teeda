package examples.teeda.web.foreach;

public class ForeachLinkPage {

	private String foo;

	private String bar;

	private FooItem[] aaaItems;

	public FooItem[] getAaaItems() {
		if (aaaItems == null) {
			aaaItems = new FooItem[3];
			aaaItems[0] = createItem("a1", "b1");
			aaaItems[1] = createItem("a2", "b2");
			aaaItems[2] = createItem("a3", "b3");
		}
		return aaaItems;
	}

	private FooItem createItem(String foo, String bar) {
		final FooItem item = new FooItem();
		item.setFoo(foo);
		item.setBar(bar);
		return item;
	}

	public void setAaaItems(FooItem[] fooItems) {
		this.aaaItems = fooItems;
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

	public String getFooStyle() {
		return "background-color:yellow";
	}

	public static class FooItem {

		private String foo;

		private String bar;

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

}
