/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
