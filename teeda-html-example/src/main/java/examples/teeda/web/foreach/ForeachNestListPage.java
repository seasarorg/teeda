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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.teeda.extension.annotation.validator.Required;

public class ForeachNestListPage {

	public List<List<Map<String, String>>> aaaItemsItems;

	public int aaaItemsIndex;

	public List<Map<String, String>> aaaItems;

	public int aaaIndex;

	@Required
	private String foo;

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public String initialize() {
		aaaItemsItems = new ArrayList<List<Map<String, String>>>();
		for (int i = 0; i < 2; i++) {
			List<Map<String, String>> items = new ArrayList<Map<String, String>>();
			for (int j = 0; j < 2; j++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("foo", String.valueOf(i) + String.valueOf(j));
				items.add(map);
			}
			aaaItemsItems.add(items);
		}

		return null;
	}

	public String prerender() {
		return null;
	}

	public void doInsertRow() {
		List<Map<String, String>> items = new ArrayList<Map<String, String>>();
		aaaItemsItems.add(0, items);
	}

	public void doAppendRow() {
		List<Map<String, String>> items = new ArrayList<Map<String, String>>();
		aaaItemsItems.add(items);
	}

	public void doInsertColumn() {
		List<Map<String, String>> items = aaaItemsItems.get(aaaItemsIndex);
		Map<String, String> map = new HashMap<String, String>();
		map.put("foo", null);
		items.add(0, map);
	}

	public void doAppendColumn() {
		List<Map<String, String>> items = aaaItemsItems.get(aaaItemsIndex);
		Map<String, String> map = new HashMap<String, String>();
		map.put("foo", null);
		items.add(map);
	}

}
