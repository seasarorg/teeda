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

public class AaaPage {

	public AaaDto[] aaaItems;
	public int aaaIndex;
	public AaaDto aaa;

	public boolean check;
	public String name;

	public void prerender() {

		aaaItems = new AaaDto[3];

		AaaDto dto1 = new AaaDto();
		dto1.setName("item1");
		AaaDto dto2 = new AaaDto();
		dto2.setName("item2");
		AaaDto dto3 = new AaaDto();
		dto3.setName("item3");

		aaaItems[0] = dto1;
		aaaItems[1] = dto2;
		aaaItems[2] = dto3;
	}

	public void doTest() {
		for (int i = 0; i < aaaItems.length; ++i) {
			AaaDto dto = aaaItems[i];
			System.out.println(dto.getName() + ", " + dto.isCheck());
		}
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLayout() {
		return null;
	}

}
