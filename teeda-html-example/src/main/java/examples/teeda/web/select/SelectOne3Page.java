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

public class SelectOne3Page {

	private List aaaItems;

	private int aaa;

	private String bbb;

	private List cccItems;

	private String name;

	public String initialize() {
		aaaItems = new ArrayList();
		Map map1 = new HashMap();
		map1.put("label", "AAAA");
		map1.put("value", new Integer(0));
		aaaItems.add(map1);
		Map map2 = new HashMap();
		map2.put("label", "BBBB");
		map2.put("value", new Integer(1));
		aaaItems.add(map2);
		Map map3 = new HashMap();
		map3.put("label", "CCCC");
		map3.put("value", new Integer(2));
		aaaItems.add(map3);
		cccItems = new ArrayList();
		Map mapCcc1 = new HashMap();
		mapCcc1.put("name", "name1");
		cccItems.add(mapCcc1);
		Map mapCcc2 = new HashMap();
		mapCcc2.put("name", "name2");
		cccItems.add(mapCcc2);
		Map mapCcc3 = new HashMap();
		mapCcc3.put("name", "name3");
		cccItems.add(mapCcc3);
		return null;
	}

	public List getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List aaaItems) {
		this.aaaItems = aaaItems;
	}

	public int getAaa() {
		return aaa;
	}

	public void setAaa(int aaa) {
		this.aaa = aaa;
	}

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

	public String doAction() {
		return null;
	}

	public List getCccItems() {
		return cccItems;
	}

	public void setCccItems(List cccItems) {
		this.cccItems = cccItems;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
