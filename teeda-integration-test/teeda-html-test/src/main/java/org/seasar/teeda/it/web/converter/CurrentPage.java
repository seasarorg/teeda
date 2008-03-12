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
import java.text.SimpleDateFormat;

import org.seasar.teeda.extension.annotation.convert.TimestampConverter;

public class CurrentPage extends AbstractPage {

	private Timestamp zikan;

	@TimestampConverter(pattern = "HH:mm", target = "doNextPage")
	public Timestamp getZikan() {
		return this.zikan;
	}

	public void setZikan(Timestamp zikan) {
		this.zikan = zikan;
	}

	public Class initialize() {
		try {
			this.selectDate = new SimpleDateFormat("yyyy/MM/dd")
					.parse("2007/10/19");
			this.zikan = new Timestamp(new SimpleDateFormat("HH:mm").parse(
					"15:00").getTime());
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Class prerender() {
		return null;
	}

	public Class doNextPage() {
		return NextPage.class;
	}

	public Class doCurrentPage() {
		return null;
	}

	public Class doBackPage() {
		return BackPage.class;
	}

}
