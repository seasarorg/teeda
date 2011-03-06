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
import java.util.ArrayList;
import java.util.List;

public class NoItemsSaveListPage {

	private String aaa;
	private String bbb;
	private String ccc;

	private List<Foo> fooItems;
	private int fooIndex;

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

	public List<Foo> getFooItems() {
		return fooItems;
	}

	public void setFooItems(List<Foo> fooItems) {
		this.fooItems = fooItems;
	}

	public int getFooIndex() {
		return fooIndex;
	}

	public void setFooIndex(int fooIndex) {
		this.fooIndex = fooIndex;
	}

	public void prerender() {
		if (fooItems == null) {
			fooItems = new ArrayList<Foo>();
			fooItems.add(createItem("a1", "b1", "c1"));
			fooItems.add(createItem("a2", "b2", "c2"));
			fooItems.add(createItem("a3", "b3", "c3"));
			fooItems.add(createItem("a4", "b4", "c4"));
			fooItems.add(createItem("a5", "b5", "c5"));
			fooItems.add(createItem("a6", "b6", "c6"));
			fooItems.add(createItem("a7", "b7", "c7"));
		}
	}

	public void doTest() {
	}

	private Foo createItem(String aaa, String bbb, String ccc) {
		Foo item = new Foo();
		item.setAaa(aaa);
		item.setBbb(bbb);
		item.setCcc(ccc);
		return item;
	}

	public static class Foo implements Serializable {

		private static final long serialVersionUID = 1L;

		private String aaa;
		private String bbb;
		private String ccc;

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
	}

}
