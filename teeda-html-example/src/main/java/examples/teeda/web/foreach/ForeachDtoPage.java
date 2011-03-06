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

import java.io.Serializable;
import java.util.Arrays;

public class ForeachDtoPage {

	private String foo;

	private String bar;

	private String baz;

	private FooItem aaa;

	private FooItem[] aaaItems;

	public String initialize() {
		System.out
				.println("<initialize>aaaItems: " + Arrays.toString(aaaItems));
		System.out.println("<initialize>aaa: " + aaa);
		System.out.println("<initialize>foo: " + foo);
		System.out.println("<initialize>bar: " + bar);
		return null;
	}

	public String prerender() {
		System.out.println("<prerender>aaaItems: " + Arrays.toString(aaaItems));
		System.out.println("<prerender>aaa: " + aaa);
		System.out.println("<prerender>foo: " + foo);
		System.out.println("<prerender>bar: " + bar);
		return null;
	}

	public Class doSubmit() {
		System.out.println("<doSubmit>aaaItems: " + Arrays.toString(aaaItems));
		System.out.println("<doSubmit>aaa: " + aaa);
		System.out.println("<doSubmit>foo: " + foo);
		System.out.println("<doSubmit>bar: " + bar);
		System.out.println("<doSubmit>baz: " + baz);
		return null;
	}

	public FooItem[] getAaaItems() {
		if (aaaItems == null) {
			aaaItems = new FooItem[3];
			aaaItems[0] = createItem("a1", "b1", "c1");
			aaaItems[1] = createItem("a2", "b2", "c2");
			aaaItems[2] = createItem("a3", "b3", "c3");
		}
		return aaaItems;
	}

	private FooItem createItem(String foo, String bar, String baz) {
		final FooItem item = new FooItem();
		item.setFoo(foo);
		item.setBar(bar);
		return item;
	}

	public void setAaaItems(FooItem[] fooItems) {
		System.out.println("<setAaaItems>aaaItems: "
				+ Arrays.toString(aaaItems));
		this.aaaItems = fooItems;
	}

	public FooItem getAaa() {
		return aaa;
	}

	public void setAaa(FooItem aaa) {
		System.out.println("<setAaa>aaa: " + aaa);
		if (aaa != null && aaa.foo == null) {
			new Throwable(aaa.toString()).printStackTrace(System.out);
		}
		this.aaa = aaa;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		System.out.println("<setFoo>foo: " + foo);
		this.foo = foo;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		System.out.println("<setBar>bar: " + bar);
		this.bar = bar;
	}

	public String getBaz() {
		return baz;
	}

	public void setBaz(String baz) {
		System.out.println("<setBaz>baz: " + baz);
		this.baz = baz;
	}

	public String getFooStyle() {
		return "background-color:yellow";
	}

	public static class FooItem implements Serializable {

		private static final long serialVersionUID = 1L;

		private String foo;

		private String bar;

		public String getFoo() {
			return foo;
		}

		public void setFoo(String foo) {
			this.foo = foo;
		}

		public String getBar() {
			return bar;
		}

		public void setBar(String bar) {
			this.bar = bar;
		}

		public String getBaz() {
			return foo + bar;
		}

		public String toString() {
			return "foo: " + foo + ", bar: " + bar;
		}
	}

}
