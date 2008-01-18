/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.it.web.converter;

import java.sql.Timestamp;
import java.util.Date;

import org.seasar.teeda.extension.annotation.convert.DateTimeConverter;
import org.seasar.teeda.extension.annotation.convert.TimestampConverter;

public class NextPage {

	private Date selectDate;

	private Timestamp zikan;

	@DateTimeConverter(pattern = "yyyy/MM/dd")
	public Date getSelectDate() {
		return this.selectDate;
	}

	public void setSelectDate(Date selectDate) {
		this.selectDate = selectDate;
	}

	@TimestampConverter(pattern = "HH:mm", target = "doNextPage")
	public Timestamp getZikan() {
		return this.zikan;
	}

	public void setZikan(Timestamp zikan) {
		this.zikan = zikan;
	}

	public Class initialize() {
		return null;
	}

	public Class prerender() {
		return null;
	}
}