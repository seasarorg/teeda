/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.it.web.teeda305;

public class Teeda305Page {
	private TestDto testDto;

	private HogeDto[] resultItems;

	private String id;

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

	public Teeda305Page() {
	}

	public void setTestDto(TestDto testDto) {
		this.testDto = testDto;
	}

	public HogeDto[] getResultItems() {
		return resultItems;
	}

	public void setResultItems(HogeDto[] resultItems) {
		this.resultItems = resultItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		testDto.setAaa(id);
		this.id = id;
	}
}
