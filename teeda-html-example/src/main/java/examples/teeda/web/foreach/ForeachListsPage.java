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

public class ForeachListsPage {

	public String[] aaaItems;
	public String aaa;
	public BbbDto[] bbbItems;
	public String title;
	public String data;

	public void initialize() {
		aaaItems = new String[3];
		aaaItems[0] = "xxx";
		aaaItems[1] = "yyy";
		aaaItems[2] = "zzz";

		bbbItems = new BbbDto[3];
		bbbItems[0] = new BbbDto("xxx", "XXX");
		bbbItems[1] = new BbbDto("yyy", "YYY");
		bbbItems[2] = new BbbDto("zzz", "ZZZ");
	}

	public static class BbbDto implements Serializable {
		private static final long serialVersionUID = 1L;
		public String title;
		public String data;

		public BbbDto() {
		}

		public BbbDto(String title, String data) {
			this.title = title;
			this.data = data;
		}
	}

}
