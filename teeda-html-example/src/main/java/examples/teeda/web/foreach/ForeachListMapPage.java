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

public class ForeachListMapPage {

	private String foo;

	private String bar;

	private List aaaItems;

	public List getAaaItems() {
		if (aaaItems == null) {
			aaaItems = new ArrayList();
			Map map = new HashMap();
			map.put("foo", "FOO");
			map.put("bar", "BAR");
			aaaItems.add(map);
			Map map2 = new HashMap();
			map2.put("foo", "FOO2");
			map2.put("bar", "BAR2");
			aaaItems.add(map2);
		}
		return aaaItems;
	}

	public void setAaaItems(List fooItems) {
		this.aaaItems = fooItems;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}
}