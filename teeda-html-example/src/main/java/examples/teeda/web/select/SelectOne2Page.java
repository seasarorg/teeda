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
package examples.teeda.web.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectOne2Page {

	private List aaaItems;

	// public static final String aaa_TRequiredValidator = null;

	private Integer aaa = new Integer(2);

	public String prerender() {
		aaaItems = new ArrayList();
		Map map1 = new HashMap();
		map1.put("label", "AAAA");
		map1.put("value", new Integer(1));
		aaaItems.add(map1);
		Map map2 = new HashMap();
		map2.put("label", "BBBB");
		map2.put("value", new Integer(2));
		aaaItems.add(map2);
		Map map3 = new HashMap();
		map3.put("label", "CCCC");
		map3.put("value", new Integer(3));
		aaaItems.add(map3);
		return null;
	}

	public List getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List aaaItems) {
		this.aaaItems = aaaItems;
	}

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

	public String doAction() {
		return null;
	}
}
