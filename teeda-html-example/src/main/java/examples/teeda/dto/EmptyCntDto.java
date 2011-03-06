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
package examples.teeda.dto;

import java.io.Serializable;

public class EmptyCntDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String emp;

	private String reserve;

	private EmptyCntDto[] emptyItems;

	private EmptyCntDto[] reserveItems;

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public EmptyCntDto[] getEmptyItems() {
		return emptyItems;
	}

	public void setEmptyItems(EmptyCntDto[] emptyItems) {
		this.emptyItems = emptyItems;
	}

	public EmptyCntDto[] getReserveItems() {
		return reserveItems;
	}

	public void setReserveItems(EmptyCntDto[] reserveItems) {
		this.reserveItems = reserveItems;
	}
}