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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ForeachDeleteListPage {

	private List<ForEachDto> aaaItems;

	private int aaaIndex;

	private String key;

	public List<ForEachDto> getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List<ForEachDto> aaaItems) {
		this.aaaItems = aaaItems;
	}

	public int getAaaIndex() {
		return aaaIndex;
	}

	public void setAaaIndex(int bbbIndex) {
		this.aaaIndex = bbbIndex;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void initialize() {
		aaaItems = new ArrayList<ForEachDto>();
		aaaItems.add(new ForEachDto("111"));
		aaaItems.add(new ForEachDto("222"));
		aaaItems.add(new ForEachDto("333"));
		aaaItems.add(new ForEachDto("444"));
		aaaItems.add(new ForEachDto("555"));
	}

	public void doDelete() {
		System.out.println("##### CLICKED INDEX[" + aaaIndex + "] #####");
		aaaItems.remove(aaaIndex);
	}

	public static class ForEachDto implements Serializable {

		private static final long serialVersionUID = 1L;

		private String key;

		public ForEachDto() {
		}

		public ForEachDto(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}
}
