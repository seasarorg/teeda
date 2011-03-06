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
import java.math.BigDecimal;
import java.util.ArrayList;

public class ForeachManyPage {

	private int itemSize;

	private FooItem[] fooItems;

	private int fooIndex;

	public String initialize() {
		itemSize = 5;
		fooItems = createItems(itemSize);
		return null;
	}

	private FooItem[] createItems(final int size) {
		final ArrayList items = new ArrayList();
		for (int i = 0; i < size; i++) {
			final FooItem item = new FooItem();
			item.setAaa("aa" + i);
			item.setBbb(new BigDecimal(i));
			final FooItem fooItem = item;
			items.add(fooItem);
		}
		FooItem[] a = new FooItem[items.size()];
		items.toArray(a);
		return a;
	}

	public String doChangeSize() {
		fooItems = createItems(itemSize);
		return null;
	}

	public int getFooIndex() {
		return fooIndex;
	}

	public void setFooIndex(int fooIndex) {
		this.fooIndex = fooIndex;
	}

	public FooItem[] getFooItems() {
		return fooItems;
	}

	public void setFooItems(FooItem[] fooItems) {
		this.fooItems = fooItems;
	}

	public int getItemSize() {
		return itemSize;
	}

	public void setItemSize(int itemSize) {
		this.itemSize = itemSize;
	}

	public static class FooItem implements Serializable {

		private String aaa;

		private BigDecimal bbb;

		public String getAaa() {
			return aaa;
		}

		public void setAaa(String foo) {
			this.aaa = foo;
		}

		public BigDecimal getBbb() {
			return bbb;
		}

		public void setBbb(BigDecimal eee) {
			this.bbb = eee;
		}

	}

	private String aaa;

	private BigDecimal bbb;

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String foo) {
		this.aaa = foo;
	}

	public BigDecimal getBbb() {
		return bbb;
	}

	public void setBbb(BigDecimal eee) {
		this.bbb = eee;
	}

}
