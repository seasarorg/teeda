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
package org.seasar.teeda.it.web.foreach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ForeachNestListPage {

	private int aaaIndex;

	private int aaaIndexIndex;

	private List<FooDto> aaaItems;

	private List<List<FooDto>> aaaItemsItems;

	private String foo;

	public int getAaaIndex() {
		return aaaIndex;
	}

	public void setAaaIndex(int aaaIndex) {
		this.aaaIndex = aaaIndex;
	}

	public int getAaaIndexIndex() {
		return aaaIndexIndex;
	}

	public void setAaaIndexIndex(int aaaIndexIndex) {
		this.aaaIndexIndex = aaaIndexIndex;
	}

	public List<FooDto> getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List<FooDto> aaaItems) {
		this.aaaItems = aaaItems;
	}

	public List<List<FooDto>> getAaaItemsItems() {
		return aaaItemsItems;
	}

	public void setAaaItemsItems(List<List<FooDto>> aaaItemsItems) {
		this.aaaItemsItems = aaaItemsItems;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public String initialize() {
		aaaItemsItems = new ArrayList<List<FooDto>>();
		for (int i = 0; i < 2; i++) {
			List<FooDto> items = new ArrayList<FooDto>();
			for (int j = 0; j < 2; j++) {
				FooDto fooDto = new FooDto();
				fooDto.setFoo(String.valueOf(i) + String.valueOf(j));
				items.add(fooDto);
			}
			aaaItemsItems.add(items);
		}

		return null;
	}

	public String prerender() {
		return null;
	}

	public static final class FooDto implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String foo;

		/**
		 * @return Returns the foo.
		 */
		public String getFoo() {
			return foo;
		}

		/**
		 * @param foo
		 *            The foo to set.
		 */
		public void setFoo(String foo) {
			this.foo = foo;
		}
	}
}
