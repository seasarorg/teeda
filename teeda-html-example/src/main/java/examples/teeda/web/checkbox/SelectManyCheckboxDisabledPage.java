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
package examples.teeda.web.checkbox;

import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.util.ArrayUtil;
import org.seasar.teeda.extension.annotation.scope.PageScope;

/**
 * @author shot
 */
public class SelectManyCheckboxDisabledPage {

	@PageScope
	private Integer[] aaa;

	@PageScope
	private List aaaItems;

	protected String aaaAsString;

	public void initialize() {
		setAaa(new Integer[] { new Integer(2) });

		ArrayList aaaItem = new ArrayList();
		AaaDto dto1 = new AaaDto();
		dto1.setValue(new Integer(0));
		dto1.setLabel("AAAA");
		aaaItem.add(dto1);
		AaaDto dto2 = new AaaDto();
		dto2.setValue(new Integer(1));
		dto2.setLabel("BBBB");
		aaaItem.add(dto2);
		AaaDto dto3 = new AaaDto();
		dto3.setValue(new Integer(2));
		dto3.setLabel("CCCC");
		aaaItem.add(dto3);
		setAaaItems(aaaItem);

	}

	public String prerender() {
		return null;
	}

	public List getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List aaaItems) {
		this.aaaItems = aaaItems;
	}

	public String doAction() {

		if (null != getAaa()) {
			for (int aa : getAaa()) {
				System.out.println("doAction getAaa()=" + aa);
			}
		}

		return null;
	}

	public Integer[] getAaa() {
		return aaa;
	}

	public void setAaa(Integer[] aaa) {
		this.aaa = aaa;
	}

	public String getAaaAsString() {
		return ArrayUtil.toString(aaa);
	}

	public void setAaaAsString(String aaaAsString) {
		this.aaaAsString = aaaAsString;
	}

}
