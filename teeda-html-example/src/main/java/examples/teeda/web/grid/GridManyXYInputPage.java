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
package examples.teeda.web.grid;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class GridManyXYInputPage {

	private int itemSize;

	private FooItem[] fooItems;

	private int fooIndex;

	private boolean show;

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public GridManyXYInputPage() {
	}

	public String getFooRowStyleClass() {
		if (fooIndex % 2 == 0) {
			return "row_even";
		}
		return "row_odd";
	}

	public String initialize() {
		itemSize = 100;
		fooItems = createItems(itemSize);
		show = true;
		return null;
	}

	private FooItem[] createItems(final int size) {
		final ArrayList items = new ArrayList();
		for (int i = 0; i < size; i++) {
			final FooItem item = new FooItem();
			item.setAaa("a" + i);
			item.setBbb("b" + i);
			item.setCcc("c" + i);
			item.setDdd("d" + i);
			item.setEee(new BigDecimal(String.valueOf(i)));
			item.setFff("f" + i);
			item.setGgg("g" + i);
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

	public FooItem[] getFooItems() {
		return fooItems;
	}

	public void setFooItems(FooItem[] fooItems) {
		this.fooItems = fooItems;
	}

	public static class FooItem implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String aaa;

		private String bbb;

		private String ccc;

		private String ddd;

		private BigDecimal eee;

		private String fff;

		private String ggg;

		public String getGgg() {
			return ggg;
		}

		public void setGgg(String ggg) {
			this.ggg = ggg;
		}

		public String getBbb() {
			return bbb;
		}

		public void setBbb(String bar) {
			this.bbb = bar;
		}

		public String getAaa() {
			return aaa;
		}

		public void setAaa(String foo) {
			this.aaa = foo;
		}

		public String getCcc() {
			return ccc;
		}

		public void setCcc(String ccc) {
			this.ccc = ccc;
		}

		public String getDdd() {
			return ddd;
		}

		public void setDdd(String ddd) {
			this.ddd = ddd;
		}

		public BigDecimal getEee() {
			return eee;
		}

		public void setEee(BigDecimal eee) {
			this.eee = eee;
		}

		public String getFff() {
			return fff;
		}

		public void setFff(String fff) {
			this.fff = fff;
		}
	}

	private String aaa;

	private String bbb;

	private String ccc;

	private String ddd;

	private BigDecimal eee;

	private String fff;

	private String ggg;

	public String getGgg() {
		return ggg;
	}

	public void setGgg(String ggg) {
		this.ggg = ggg;
	}

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bar) {
		this.bbb = bar;
	}

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String foo) {
		this.aaa = foo;
	}

	public String getCcc() {
		return ccc;
	}

	public void setCcc(String ccc) {
		this.ccc = ccc;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public BigDecimal getEee() {
		return eee;
	}

	public void setEee(BigDecimal eee) {
		this.eee = eee;
	}

	public String getFff() {
		return fff;
	}

	public void setFff(String fff) {
		this.fff = fff;
	}

	public void setFooIndex(int fooIndex) {
		this.fooIndex = fooIndex;
	}

	public int getItemSize() {
		return itemSize;
	}

	public void setItemSize(int itemSize) {
		this.itemSize = itemSize;
	}

}
