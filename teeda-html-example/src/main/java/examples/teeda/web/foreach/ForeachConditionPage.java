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

import org.seasar.teeda.extension.annotation.validator.Required;

public class ForeachConditionPage {

	@Required
	public String required;

	public String selected;

	public ForEachDto[] aaaItems;

	public int aaaIndex;

	public String key;

	public void initialize() {
		aaaItems = new ForEachDto[] { new ForEachDto("1"), new ForEachDto("2"),
				new ForEachDto("3"), new ForEachDto("4"), new ForEachDto("5") };
	}

	public boolean isEven() {
		return (aaaIndex + 1) % 2 == 0;
	}

	public boolean isOdd() {
		return (aaaIndex + 1) % 2 != 0;
	}

	public void doEven() {
		selected = "even selected.";
	}

	public void doOdd() {
		selected = "odd selected.";
	}

	public static class ForEachDto implements Serializable {

		private static final long serialVersionUID = 1L;

		public String key;

		public ForEachDto() {
		}

		public ForEachDto(String key) {
			this.key = key;
		}
	}
}
