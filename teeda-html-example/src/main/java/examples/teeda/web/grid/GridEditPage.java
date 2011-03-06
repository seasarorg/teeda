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
package examples.teeda.web.grid;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import examples.teeda.web.common.PageUtil;
import examples.teeda.web.dto.FooDto;

public class GridEditPage extends BaseGridEditPage {

	private NumberFormat formater = new DecimalFormat("0.0000#");

	private String sortId;

	private String sortOrder;

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public GridEditPage() {
	}

	public String initialize() {

		if (super.fooItems == null) {
			super.fooItems = new FooDto[6];
			for (int i = 0; i < super.fooItems.length; i++) {
				Integer aaa = new Integer(i);
				String bbb = "str" + i;
				BigDecimal ccc = new BigDecimal(this.formater.format(aaa
						.intValue()));
				super.fooItems[i] = new FooDto(aaa, bbb, ccc);
			}
		}

		return null;
	}

	public String prerender() {
		return null;
	}

	public String doAdd() {
		if (super.fooItems == null) {
			super.fooItems = new FooDto[0];
		}

		int size = super.fooItems.length;
		FooDto[] newArray = new FooDto[size + 1];
		System.arraycopy(super.fooItems, 0, newArray, 0, size);

		FooDto dto = new FooDto();
		dto.setEditStatus(PageUtil.EDIT_ADD);
		newArray[size] = dto;

		this.fooItems = newArray;

		return null;
	}

	public String doDelete() {
		if (super.fooItems == null || super.fooItems.length <= 0) {
			return null;
		}

		if ((super.fooIndexSelect == null)
				|| (super.fooIndexSelect.intValue() > super.fooItems.length)) {
			return null;
		}

		FooDto dto = this.fooItems[this.fooIndexSelect.intValue()];
		dto.setEditStatus(PageUtil.EDIT_DELETE);

		// 実際に配列を削除する場合
		// List fooList = new ArrayList(Arrays.asList(this.fooItems));
		// fooList.remove(this.fooIndexSelect.intValue());
		// this.fooItems = (FooDto[]) fooList.toArray(new FooDto[0]);

		return null;
	}

	public String doUpdate() {
		return null;
	}

	public String doSort() {
		System.out.println("sortId=" + this.sortId + ", sortOrder="
				+ this.sortOrder);
		return null;
	}

}
