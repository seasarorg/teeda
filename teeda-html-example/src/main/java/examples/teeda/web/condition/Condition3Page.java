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
package examples.teeda.web.condition;

import java.util.ArrayList;
import java.util.List;

public class Condition3Page {

	public static final String name_TRequiredValidator = null;

	private int flgInfoIndex;

	private List flgInfoItems;

	private Boolean flgInfo;

	private String name;

	public int getFlgInfoIndex() {
		return flgInfoIndex;
	}

	public void setFlgInfoIndex(int flgInfoIndex) {
		this.flgInfoIndex = flgInfoIndex;
	}

	public List getFlgInfoItems() {
		return flgInfoItems;
	}

	public void setFlgInfoItems(List/* <Boolean> */flgInfoItems) {
		this.flgInfoItems = flgInfoItems;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class doCheck() {
		return null;
	}

	public Class initialize() {
		return null;
	}

	public Class prerender() {
		flgInfoItems = new ArrayList/* <Boolean> */();
		flgInfoItems.add(Boolean.TRUE);
		flgInfoItems.add(Boolean.FALSE);
		flgInfoItems.add(Boolean.TRUE);
		return null;
	}

	public boolean isDisp() {
		if (flgInfo == null) {
			return false;
		}
		return flgInfo.booleanValue();
	}

	/**
	 * @return the flgInfo
	 */
	public Boolean getFlgInfo() {
		return flgInfo;
	}

	/**
	 * @param flgInfo
	 *            the flgInfo to set
	 */
	public void setFlgInfo(Boolean flgInfo) {
		this.flgInfo = flgInfo;
	}

}
