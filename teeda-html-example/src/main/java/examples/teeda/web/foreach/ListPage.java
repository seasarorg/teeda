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

import examples.teeda.dto.EmptyCntDto;

public class ListPage {

	private EmptyCntDto[] listItems;

	private String reserve;

	private EmptyCntDto[] reserveItems;

	private String emp;

	private EmptyCntDto[] emptyItems;

	public Class initialize() {
		return null;
	}

	public Class prerender() {

		listItems = new EmptyCntDto[10];

		for (int i = 0; i < 10; i++) {
			EmptyCntDto[] items1 = new EmptyCntDto[10];
			EmptyCntDto[] items2 = new EmptyCntDto[10];

			for (int n = 0; n < 10; n++) {
				items1[n] = new EmptyCntDto();
				items1[n].setEmp("emp = " + n);
				items2[n] = new EmptyCntDto();
				items2[n].setReserve("reserve = " + n);
			}
			listItems[i] = new EmptyCntDto();
			listItems[i].setEmptyItems(items1);
			listItems[i].setReserveItems(items2);
		}

		return null;
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public EmptyCntDto[] getEmptyItems() {
		return emptyItems;
	}

	public void setEmptyItems(EmptyCntDto[] emptyItems) {
		this.emptyItems = emptyItems;
	}

	public EmptyCntDto[] getListItems() {
		return listItems;
	}

	public void setListItems(EmptyCntDto[] listItems) {
		this.listItems = listItems;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public EmptyCntDto[] getReserveItems() {
		return reserveItems;
	}

	public void setReserveItems(EmptyCntDto[] reserveItems) {
		this.reserveItems = reserveItems;
	}

}
