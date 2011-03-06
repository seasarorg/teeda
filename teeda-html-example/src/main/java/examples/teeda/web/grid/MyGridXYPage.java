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

import java.util.ArrayList;
import java.util.List;

public class MyGridXYPage {

	private List<FooItem> fooItems;

	private String aaa;
	private String bbb;
	private String ccc;
	private String ddd;
	private String eee;
	private String fff;

	public void prerender() {

		fooItems = new ArrayList<FooItem>();
		for (int i = 0; i < 8; i++) {
			fooItems.add(createItem("a" + i, "b" + i, "c" + i, "d" + i,
					"e" + i, "f" + i, "g" + i));

		}

	}

	private FooItem createItem(String aaa, String bbb, String ccc, String ddd,
			String eee, String fff, String ggg) {

		final FooItem item = new FooItem();
		item.setAaa(aaa);
		item.setBbb(bbb);

		// 値が空だとなぜか2行目以降の<span>内のDOM操作が反映されない
		// 下記コメントを解除して<span></span>内に文字があると2行目以降のDOM操作も反映される
		item.setCcc("\u00A0");
		item.setDdd("\u00A0");
		item.setEee("\u00A0");
		item.setFff("\u00A0");

		return item;
	}

	public List<FooItem> getFooItems() {

		return fooItems;
	}

	public void setFooItems(List<FooItem> fooItems) {
		this.fooItems = fooItems;
	}

	public String getLayout() {
		return null;
	}

	public static class FooItem {

		// public FooItem(){
		// this.aaa = "";
		// this.bbb = "";
		// this.ccc = "あ";
		// this.ddd = "い";
		// this.eee = "う";
		// this.fff = "え";
		//			
		// }

		private String aaa;
		private String bbb;
		private String ccc;
		private String ddd;
		private String eee;
		private String fff;

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

		public String getDdd() {
			return ddd;
		}

		public void setDdd(String ddd) {
			this.ddd = ddd;
		}

		public String getEee() {
			return eee;
		}

		public void setEee(String eee) {
			this.eee = eee;
		}

		public String getFff() {
			return fff;
		}

		public void setFff(String fff) {
			this.fff = fff;
		}

	}

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

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getEee() {
		return eee;
	}

	public void setEee(String eee) {
		this.eee = eee;
	}

	public String getFff() {
		return fff;
	}

	public void setFff(String fff) {
		this.fff = fff;
	}
}
