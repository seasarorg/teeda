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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import examples.teeda.web.common.PageUtil;
import examples.teeda.web.dto.FooDto;

public class ForeachGridEditPage extends AbstractForeachGridPage {

	private List barItems = new ArrayList();

	private Integer bar;

	public ForeachGridEditPage() {
	}

	public String initialize() {
		if (super.fooItems == null) {
			super.fooItems = new FooDto[5];
			for (int i = 0; i < super.fooItems.length; i++) {
				Integer aaa = new Integer(i);
				String bbb = "str" + i;
				BigDecimal ccc = new BigDecimal(aaa.toString());
				ccc = ccc.movePointRight(4);
				super.fooItems[i] = new FooDto(aaa, bbb, ccc);
			}
		}

		for (int i = 0; i < 10; i++) {
			Map map = new HashMap();
			map.put("value", new Integer(i));
			map.put("label", "col" + i);
			this.barItems.add(map);
		}
		return null;
	}

	public String prerender() {
		return null;
	}

	public List getBarItems() {
		return barItems;
	}

	public void setBarItems(List barItems) {
		this.barItems = barItems;
	}

	public Integer getBar() {
		return bar;
	}

	public void setBar(Integer bar) {
		this.bar = bar;
	}

	public String doAdd() {
		if (this.bar == null) {
			return null;
		}

		// 削除された要素以外に指定された識別子がある場合は、
		// 既に要素が登録済みであるため、追加処理は行わない。
		boolean isExist = false;
		for (int i = 0; i < this.fooItems.length; i++) {
			FooDto dto = this.fooItems[i];
			if ((dto.getEditStatus() == PageUtil.EDIT_DELETE)
					&& (dto.getAaa() != null)
					&& (this.bar.intValue() == dto.getAaa().intValue())) {
				isExist = true;
				break;
			}
		}

		if (isExist == false) {
			int size = this.fooItems.length;
			FooDto[] newArray = new FooDto[size + 1];
			System.arraycopy(this.fooItems, 0, newArray, 0, size);

			FooDto dto = new FooDto();
			dto.setAaa(this.bar);
			dto.setEditStatus(PageUtil.EDIT_ADD);
			newArray[size] = dto;

			this.fooItems = newArray;
		}

		return null;
	}

	public String doDelete() {
		if (this.bar == null) {
			return null;
		}

		// 以前に同じ値を持つ要素が削除されている可能性があるため、ブレイクしない。
		for (int i = 0; i < this.fooItems.length; i++) {
			FooDto dto = this.fooItems[i];
			if ((dto.getAaa() != null)
					&& (this.bar.intValue() == dto.getAaa().intValue())) {
				dto.setEditStatus(PageUtil.EDIT_DELETE);
			}
		}
		return null;
	}

}
