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
package examples.teeda.web.select;

import java.util.LinkedHashMap;
import java.util.Map;

import org.seasar.framework.util.ArrayUtil;
import org.seasar.teeda.extension.annotation.scope.PageScope;

public class SelectManyListboxMapItemsDisabledPage {
	@PageScope
	public Map<String, Integer> aaaItems;
	@PageScope
	public Integer[] aaa;

	protected String aaaAsString;

	public String initialize() {
		aaaItems = new LinkedHashMap<String, Integer>();
		aaaItems.put("AAAA", 0);
		aaaItems.put("BBBB", 1);
		aaaItems.put("CCCC", 2);
		setAaa(new Integer[] { 1, 2 });
		return null;
	}

	public String prerender() {
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

	public String doAction() {
		if (null != aaa) {
			for (Integer aaaValue : aaa) {
				System.out.println("doAction called getAaa()=" + aaaValue);
			}
		}
		return null;
	}

}
