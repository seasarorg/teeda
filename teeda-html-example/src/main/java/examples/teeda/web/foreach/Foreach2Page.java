package examples.teeda.web.foreach;

import java.util.ArrayList;
import java.util.List;

public class Foreach2Page {

	private String foo;

	private String bar;

	private List aaaItems;

	public List getAaaItems() {
		if (aaaItems == null) {
			aaaItems = new ArrayList();
			aaaItems.add(createItem("a1", "b1"));
			aaaItems.add(createItem("a2", "b2"));
			aaaItems.add(createItem("a3", "b3"));
		}
		return aaaItems;
	}

	private FooItem createItem(String foo, String bar) {
		final FooItem item = new FooItem();
		item.setFoo(foo);
		item.setBar(bar);
		return item;
	}

	public void setAaaItems(List fooItems) {
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
