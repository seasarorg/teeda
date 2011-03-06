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

/**
 * @author yone
 */
public class ForEachListPage {

	private ForEachDto[] aaaItems;

	private int aaaIndex;

	private int clickIndex;

	private String key;

	public ForEachDto[] getAaaItems() {
		aaaItems = new ForEachDto[] { new ForEachDto(), new ForEachDto(),
				new ForEachDto(), new ForEachDto(), new ForEachDto() };
		aaaItems[0].setKey("111");
		aaaItems[1].setKey("222");
		aaaItems[2].setKey("333");
		aaaItems[3].setKey("444");
		aaaItems[4].setKey("555");
		return aaaItems;
	}

	public void setAaaItems(ForEachDto[] bbbItems) {
		this.aaaItems = bbbItems;
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

	public int getClickIndex() {
		return this.clickIndex;
	}

	public void setClickIndex(int clickIndex) {
		this.clickIndex = clickIndex;
	}

	public String doForEachResult() {
		System.out.println("##### CLICKED INDEX[" + clickIndex + "] #####");
		return "forEachResult";
	}

	public static class ForEachDto implements Serializable {

		private static final long serialVersionUID = 1L;

		private String key;

		public ForEachDto() {
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}
}
