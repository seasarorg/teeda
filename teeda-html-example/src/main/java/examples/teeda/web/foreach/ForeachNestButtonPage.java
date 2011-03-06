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

public class ForeachNestButtonPage {

	private int aaaIndex;

	private int aaaItemsIndex;

	private String aaa;

	private String[] aaaItems;

	private String[][] aaaItemsItems;

	private String selectedIndex;

	public int getAaaIndex() {
		return aaaIndex;
	}

	public void setAaaIndex(int aaaIndex) {
		this.aaaIndex = aaaIndex;
	}

	public int getAaaItemsIndex() {
		return aaaItemsIndex;
	}

	public void setAaaItemsIndex(int aaaItemsIndex) {
		this.aaaItemsIndex = aaaItemsIndex;
	}

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public String[] getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(String[] aaaItems) {
		this.aaaItems = aaaItems;
	}

	public String[][] getAaaItemsItems() {
		return aaaItemsItems;
	}

	public void setAaaItemsItems(String[][] aaaItemsItems) {
		this.aaaItemsItems = aaaItemsItems;
	}

	public String getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(String selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public void initialize() {
	}

	public void prerender() {
		aaaItemsItems = new String[3][4];
		for (int i = 0; i < 3; i++) {
			aaaItemsItems[i] = new String[4];
			for (int j = 0; j < 4; j++) {
				aaaItemsItems[i][j] = "" + i + ":" + j;
			}
		}
	}

	public String getDoOnceActionValue() {
		return aaa;
	}

	public void doOnceAction() {
		selectedIndex = "" + aaaItemsIndex + ":" + aaaIndex;
	}

}
