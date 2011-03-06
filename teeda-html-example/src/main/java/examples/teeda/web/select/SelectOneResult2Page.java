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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author shot
 */
public class SelectOneResult2Page {

	private Integer aaa;
	
	private Integer bbb;

	private String ccc;
	
	private List aaaItems;

	public String prerender() {
		for (Iterator itr = aaaItems.iterator(); itr.hasNext();) {
			Map map = (Map) itr.next();
			final Object val = map.get("value");
			if(val != null && val.equals(aaa)) {
				ccc = (String) map.get("label");
				break;
			}
		}
		return null;
	}

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

	public List getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List aaaItems) {
		this.aaaItems = aaaItems;
	}

	public Integer getBbb() {
		return bbb;
	}

	public void setBbb(Integer bbb) {
		this.bbb = bbb;
	}

	public String getCcc() {
		return ccc;
	}

	public void setCcc(String ccc) {
		this.ccc = ccc;
	}

}
