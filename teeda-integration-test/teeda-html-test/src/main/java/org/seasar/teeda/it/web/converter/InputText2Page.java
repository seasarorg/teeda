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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author yone
 * 
 */
public class InputText2Page {

	private static DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd",
			Locale.getDefault());

	public static final String aaa_TTimestampConverter = "target=doSomething";

	private Timestamp aaa;

	public String initialize() {
//		long time = 0;
//		try {
//			time = formatter.parse("2007/01/01").getTime();
//		} catch (ParseException e) {
//		}
//		aaa = new Timestamp(time);
		return null;
	}

	public String doSomething() {
		return null;
	}
	
	public String doBbb() {
		return null;
	}

	public Timestamp getAaa() {
		return aaa;
	}

	public void setAaa(Timestamp aaa) {
		this.aaa = aaa;
	}

}
