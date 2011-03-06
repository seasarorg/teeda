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
package examples.teeda.web.foreach.test;

/**
 * @author yone
 */
public class ForeachNest2Page {
	private int aaaIndex;

	private String foo;

	private HogeItem[] aaaItems;
	
	private BarItem[] bbbItems;
	
	private String bar;

	public String doTest() {
		return null;
	}
	
	public int getAaaIndex() {
		return aaaIndex;
	}

	public void setAaaIndex(int aaaIndex) {
		this.aaaIndex = aaaIndex;
	}
	
	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}	
	public void setAaaItems(HogeItem[] fooItems) {
		this.aaaItems = fooItems;
	}
	
	public HogeItem[] getAaaItems() {
		if (aaaItems == null) {
			aaaItems = createHogeItems(3);
		}
		return aaaItems;
	}
	
	private HogeItem[] createHogeItems(int size) {
		HogeItem[] hoges = new HogeItem[size];
		for (int i = 0; i < size; i++) {
			hoges[i] = new HogeItem();
			hoges[i].setFoo("Foo " + i);
			hoges[i].setBbbItems(createBarItems(size, i));
		}
		return hoges;
	}
	
	private BarItem[] createBarItems(int size, int index) {
		BarItem[] bars = new BarItem[size];
		for (int i = 0; i < size; i++) {
			bars[i] = new BarItem();
			bars[i].setBar("Bar " + index + "-" + i);
		}
		return bars;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}	

	public BarItem[] getBbbItems() {
		return bbbItems;
	}

	public void setBbbItems(BarItem[] bbbItems) {
		this.bbbItems = bbbItems;
	}

}