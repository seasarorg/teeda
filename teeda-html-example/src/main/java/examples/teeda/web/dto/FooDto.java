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
package examples.teeda.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class FooDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer aaa;

	private String bbb;

	private BigDecimal ccc;

	private int editStatus;

	private Integer bbbEditStatus;

	public FooDto() {
	}

	public FooDto(Integer aaa, String bbb, BigDecimal ccc) {
		this.aaa = aaa;
		this.bbb = bbb;
		this.ccc = ccc;
	}

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

	public BigDecimal getCcc() {
		return ccc;
	}

	public void setCcc(BigDecimal ccc) {
		this.ccc = ccc;
	}

	public int getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(int editStatus) {
		this.editStatus = editStatus;
		System.out.println("[editStatus] " + editStatus);
	}

	public Integer getBbbEditStatus() {
		return bbbEditStatus;
	}

	public void setBbbEditStatus(Integer bbbEditStatus) {
		this.bbbEditStatus = bbbEditStatus;
	}

	// public String toString() {
	// StringBuffer buff = new StringBuffer();
	// buff.append("aaa=" + this.aaa);
	// buff.append(",bbb=" + this.bbb);
	// buff.append(",ccc=" + this.ccc);
	// buff.append(",editStatus=" + this.editStatus);
	// buff.append(",bbbEditStatus=" + this.bbbEditStatus.intValue());
	//
	// return buff.toString();
	// }

}
