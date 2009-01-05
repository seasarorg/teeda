/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.it.web.foreach;

import javax.faces.internal.PhaseUtil;

public class SimpleForeachPage {

	private String foo;

	private String bar;

	private FooItem[] aaaItems;

	private int aaaIndex;

	public void initialize() {
		num = 0;
	}

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

	private int num = 0;

	public String getFooStyle() {
		System.out.println(PhaseUtil.getCurrentPhase() + " : " + (num++));
		if (aaaIndex % 2 == 0) {
			return "background-color:yellow";
		} else {
			return "background-color:red";
		}
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
