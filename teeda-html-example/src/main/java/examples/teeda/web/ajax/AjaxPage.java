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
package examples.teeda.web.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjaxPage {

	private String sample1;

	private List sample1Items;

	private String sample2;

	private List sample2Items;
	
	public Class initialize(){
		if(this.sample1Items == null){
			this.sample1Items = new ArrayList();
			for(int i = 0; i < 3; i++){
				SelectItemDto dto = new SelectItemDto();
				dto.setLabel("事業所 "+Integer.toString(i));
				dto.setValue(i);
				this.sample1Items.add(dto);
			}			
		}
		if(this.sample2Items == null){
			this.sample2Items = new ArrayList();			
		}
		
		return null;
	}
	
	public String ajaxStartAjax() {
		return "Start Ajax";
	}

	public NameDto ajaxStartAjaxObject() {
		NameDto dto = new NameDto();
		dto.setFirstName("foo");
		dto.setLastName("bar");
		return dto;
	}

	public Map ajaxStartAjaxList() {
		List list = new ArrayList();
		for (int i = 0; i < 10; i++) {
			ListDto dto = new ListDto();
			dto.setNo(i);
			dto.setName("user" + Integer.toString(i));
			list.add(dto);
		}
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	public List ajaxCombinationList(int no) {
		List list = new ArrayList();
		for(int i = 0; i < 5; i++){
			SelectItemDto dto = new SelectItemDto();
			dto.setLabel("事業所 "+Integer.toString(no)+"の部署 "+Integer.toString(i));
			dto.setValue(i);
			list.add(dto);			
		}
		return list;
	}

	public String getSample1() {
		return sample1;
	}

	public void setSample1(String sample1) {
		this.sample1 = sample1;
	}

	public String getSample2() {
		return sample2;
	}

	public void setSample2(String sample2) {
		this.sample2 = sample2;
	}

	public List getSample1Items() {
		return sample1Items;
	}

	public void setSample1Items(List sample1Items) {
		this.sample1Items = sample1Items;
	}

	public List getSample2Items() {
		return sample2Items;
	}

	public void setSample2Items(List sample2Items) {
		this.sample2Items = sample2Items;
	}

}
