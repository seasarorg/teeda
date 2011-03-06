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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForeachRadioPage {

	private String aaa;

	private List aaaItems = new ArrayList();

	private int dtoIndex;

	private MyDto[] dtoItems;

	private MyDto dto;

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public int getDtoIndex() {
		return dtoIndex;
	}

	public void setDtoIndex(int myDtoIndex) {
		this.dtoIndex = myDtoIndex;
	}

	public MyDto[] getDtoItems() {
		return dtoItems;
	}

	public void setDtoItems(MyDto[] dtoItems) {
		this.dtoItems = dtoItems;
	}

	/**
	 * @return the dto
	 */
	public MyDto getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            the dto to set
	 */
	public void setDto(MyDto dto) {
		this.dto = dto;
	}

	public Class initialize() {
		dtoItems = new MyDto[2];
		MyDto myDto = new MyDto();
		myDto.setAaa("1");
		dtoItems[0] = myDto;
		myDto = new MyDto();
		myDto.setAaa("0");
		dtoItems[1] = myDto;

		Map m = new HashMap();
		m.put("label", "aaa");
		m.put("value", "0");
		aaaItems.add(m);
		m = new HashMap();
		m.put("label", "bbb");
		m.put("value", "1");
		aaaItems.add(m);
		m = new HashMap();
		m.put("label", "ccc");
		m.put("value", "2");
		aaaItems.add(m);
		return null;
	}

	public Class prerender() {
		return null;
	}

	public Class doSubmit() {
		System.out.println("doSubmit");
		return null;
	}

	/**
	 * @return the aaaItems
	 */
	public List getAaaItems() {
		return aaaItems;
	}

	/**
	 * @param aaaItems
	 *            the aaaItems to set
	 */
	public void setAaaItems(List aaaItems) {
		this.aaaItems = aaaItems;
	}
}
