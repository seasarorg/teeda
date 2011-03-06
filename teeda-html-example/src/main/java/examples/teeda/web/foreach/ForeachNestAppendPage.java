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
 * @author koichik
 */
public class ForeachNestAppendPage {
	// field
	public int parentIndex;

	public ParentDto[] parentItems;

	public int childIndex;

	public ChildDto[] childItems;

	public String foo;

	// method
	public Class initialize() {
		parentItems = new ParentDto[2];

		parentItems[0] = new ParentDto();
		parentItems[1] = new ParentDto();

		parentItems[0].childItems = new ChildDto[3];

		parentItems[0].childItems[0] = new ChildDto();
		parentItems[0].childItems[0].foo = "1";

		parentItems[0].childItems[1] = new ChildDto();
		parentItems[0].childItems[1].foo = "2";

		parentItems[0].childItems[2] = new ChildDto();
		parentItems[0].childItems[2].foo = "3";

		parentItems[1].childItems = new ChildDto[1];

		parentItems[1].childItems[0] = new ChildDto();
		parentItems[1].childItems[0].foo = "1";

		return null;
	}

	public Class prerender() {
		return null;
	}

	public Class doAppendLast() {
		ChildDto[] tempChildDto = new ChildDto[4];

		tempChildDto[0] = parentItems[0].childItems[0];
		tempChildDto[1] = parentItems[0].childItems[1];
		tempChildDto[2] = parentItems[0].childItems[2];
		tempChildDto[3] = new ChildDto();
		tempChildDto[3].foo = "10";

		parentItems[0].childItems = tempChildDto;

		for (ChildDto dto : parentItems[0].childItems) {
			System.out.println(dto.foo);
		}

		return null;
	}

	public Class doAppendInsert() {
		ChildDto[] tempChildDto = new ChildDto[4];

		tempChildDto[0] = parentItems[0].childItems[0];
		tempChildDto[1] = parentItems[0].childItems[1];
		tempChildDto[2] = new ChildDto();
		tempChildDto[2].foo = "10";
		tempChildDto[3] = parentItems[0].childItems[2];

		parentItems[0].childItems = tempChildDto;

		for (ChildDto dto : parentItems[0].childItems) {
			System.out.println(dto.foo);
		}

		return null;
	}

	// Inner class
	public static final class ParentDto implements Serializable {
		private static final long serialVersionUID = 1L;
		public ChildDto[] childItems;
	}

	public static final class ChildDto implements Serializable {
		private static final long serialVersionUID = 1L;
		public String foo;

		public String getFoo() {
			System.out.println(foo);
			return foo;
		}

		public void setFoo(String foo) {
			System.out.println(foo);
			this.foo = foo;
		}
	}
}
