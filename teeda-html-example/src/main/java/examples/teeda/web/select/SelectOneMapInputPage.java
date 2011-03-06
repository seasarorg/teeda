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

import java.util.Map;
import java.util.TreeMap;

/**
 * @author shot
 */
public class SelectOneMapInputPage {

	public static final String bbb_lengthValidator = "minimum=3";

	private Integer aaa;

	private Map aaaItems;

	private Integer bbb;

	public Integer getBbb() {
		return bbb;
	}

	public void setBbb(Integer bbb) {
		this.bbb = bbb;
	}

	public String initialize() {
		aaaItems = new TreeMap();
		aaaItems.put("AAAA", new Integer(1));
		aaaItems.put("BBBB", new Integer(2));
		aaaItems.put("CCCC", new Integer(3));
		return null;
	}

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

	public Map getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(Map aaaItems) {
		this.aaaItems = aaaItems;
	}

}
