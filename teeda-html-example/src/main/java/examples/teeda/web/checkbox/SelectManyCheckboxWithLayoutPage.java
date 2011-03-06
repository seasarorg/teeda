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
package examples.teeda.web.checkbox;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shot
 */
public class SelectManyCheckboxWithLayoutPage {

	public static final String aaa_TRequiredValidator = null;

	private int[] aaa = new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 };

	private List aaaItems;

	private int aaaCol = 2;

	public String prerender() {
		aaaItems = new ArrayList();
		for (int i = 0; i < 20; ++i) {
			AaaDto dto = new AaaDto();
			dto.setValue(i);
			dto.setLabel(Integer.toString(i));
			aaaItems.add(dto);
		}
		return null;
	}

	public List getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List aaaItems) {
		this.aaaItems = aaaItems;
	}

	public String doAction() {
		return null;
	}

	public int[] getAaa() {
		return aaa;
	}

	public void setAaa(int[] aaa) {
		this.aaa = aaa;
	}

	public int getAaaCol() {
		return aaaCol;
	}

	public void setAaaCol(int aaaCol) {
		this.aaaCol = aaaCol;
	}
}
