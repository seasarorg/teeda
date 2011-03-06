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

public class ForeachPage {

	public String foo;

	public String bar;

	public FooItem[] aaaItems;

	public int aaaIndex;

	public Class prerender() {
		aaaItems = new FooItem[3];
		aaaItems[0] = createItem("a1", "b1");
		aaaItems[1] = createItem("a2", "b2");
		aaaItems[2] = createItem("a3", "b3");
		return null;
	}

	private FooItem createItem(String foo, String bar) {
		final FooItem item = new FooItem();
		item.foo = foo;
		item.bar = bar;
		return item;
	}

	public String getFooStyle() {
		if (aaaIndex % 2 == 0) {
			return "background-color:yellow";
		} else {
			return "background-color:red";
		}
	}

	public String getRowStyleClass() {
		return (aaaIndex + 1) % 2 == 0 ? "even" : "odd";
	}

	public static class FooItem {

		public String foo;

		public String bar;
	}
}
