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

import java.math.BigDecimal;

public class GridButtonPage {

	private FooItem[] fooItems;

	private int fooIndex;

	public String getFooRowClass() {
		if (fooIndex % 2 == 0) {
			return "row_even";
		}
		return "row_odd";
	}

	public String getFooRowStyle() {
		if (fooIndex % 2 == 0) {
			return "color : red";
		}
		return "color : blue";
	}

	public FooItem[] getFooItems() {
		if (fooItems == null) {
			fooItems = new FooItem[7];
			fooItems[0] = createItem("a1", "b1", "c1", "d1", new BigDecimal(
					"11111111"), "f1", "g1");
			fooItems[1] = createItem("a2", "b2", "c2", "d2", new BigDecimal(
					"2222"), "f2", "g2");
			fooItems[2] = createItem("a3", "b3", "c3", "d3", new BigDecimal(
					"33333"), "f3", "g3");
			fooItems[3] = createItem("a4", "b4", "c4", "d4", new BigDecimal(
					"44"), "f4", "g4");
			fooItems[4] = createItem("a5", "b5", "c5", "d5",
					new BigDecimal("5"), "f5", "g5");
			fooItems[5] = createItem("a6", "b6", "c6", "d6", new BigDecimal(
					"-6"), "f6", "g6");
			fooItems[6] = createItem("a7", "b7", "c7", "d7",
					new BigDecimal("0"), "f7", "g7");
		} else {
			FooItem[] newItems = new FooItem[7];
			newItems[0] = createItem("a1", "b1", "c1", fooItems[0].getDdd(),
					fooItems[0].getEee(), "f1", "g1");
			newItems[1] = createItem("a2", "b2", "c2", fooItems[1].getDdd(),
					fooItems[1].getEee(), "f2", "g2");
			newItems[2] = createItem("a3", "b3", "c3", fooItems[2].getDdd(),
					fooItems[2].getEee(), "f3", "g3");
			newItems[3] = createItem("a4", "b4", "c4", fooItems[3].getDdd(),
					fooItems[3].getEee(), "f4", "g4");
			newItems[4] = createItem("a5", "b5", "c5", fooItems[4].getDdd(),
					fooItems[4].getEee(), "f5", "g5");
			newItems[5] = createItem("a6", "b6", "c6", fooItems[5].getDdd(),
					fooItems[5].getEee(), "f6", "g6");
			newItems[6] = createItem("a7", "b7", "c7", fooItems[6].getDdd(),
					fooItems[6].getEee(), "f7", "g7");
			fooItems = newItems;
		}
		return fooItems;
	}

	private FooItem createItem(String aaa, String bbb, String ccc, String ddd,
			BigDecimal eee, String fff, String ggg) {
		final FooItem item = new FooItem();
		item.setAaa(aaa);
		item.setBbb(bbb);
		item.setCcc(ccc);
		item.setDdd(ddd);
		item.setEee(eee);
		item.setFff(fff);
		item.setGgg(ggg);
		return item;
	}

	public void setFooItems(FooItem[] fooItems) {
		this.fooItems = fooItems;
	}

	public String getDoActionValue() {
		return fooItems[fooIndex].getFff();
	}

	public Class doAction() {
		fff = fooItems[fooIndex].getFff();
		ggg = fooItems[fooIndex].getGgg();
		return GridButtonResultPage.class;
	}

	public static class FooItem {

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

}
