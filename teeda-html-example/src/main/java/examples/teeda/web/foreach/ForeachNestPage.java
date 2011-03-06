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

public class ForeachNestPage {

	private int aaaIndex;

	private int aaaIndexIndex;

	private FooDto[] aaaItems;

	private FooDto[][] aaaItemsItems;

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

	public FooDto[] getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(FooDto[] aaaItems) {
		this.aaaItems = aaaItems;
	}

	public FooDto[][] getAaaItemsItems() {
		return aaaItemsItems;
	}

	public void setAaaItemsItems(FooDto[][] aaaItemsItems) {
		this.aaaItemsItems = aaaItemsItems;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public String initialize() {
		aaaItemsItems = new FooDto[2][2];
		for (int i = 0; i < 2; i++) {
			FooDto[] items = new FooDto[2];
			for (int j = 0; j < 2; j++) {
				FooDto fooDto = new FooDto();
				fooDto.setFoo(String.valueOf(i) + String.valueOf(j));
				items[j] = fooDto;
			}
			aaaItemsItems[i] = items;
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
